package bkd.src.salus.api.Application.Service.Resume;

import bkd.src.salus.api.Application.Service.Tratment.IDayMedicineService;
import bkd.src.salus.api.Domain.DTO.Medicine.MedicineCalendarDetailing;
import bkd.src.salus.api.Domain.DTO.Resume.DayResume;
import bkd.src.salus.api.Domain.DTO.Resume.HasPendency;
import bkd.src.salus.api.Domain.DTO.Resume.MedicineResume;
import bkd.src.salus.api.Domain.DTO.Resume.TreatmentResume;
import bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume.MedicineConsumeLog;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.IResumeService;
import bkd.src.salus.api.Domain.Interface.Application.Treatment.ITreatmentMedicineService;
import bkd.src.salus.api.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.api.Repository.NoSQL.MedicineLog.IMedicineConsumeLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResumeService implements IResumeService {

    private final IRedisStackManager redisStackManager;
    private final IDayMedicineService treatmentMedicineService;
    private final IMedicineConsumeLogRepositoryMR consumeLogRepositoryMR;
    private final IUserRepositoryJPA userRepositoryJPA;

    @Autowired
    public ResumeService(IRedisStackManager redisStackManager, IDayMedicineService treatmentMedicineService, IMedicineConsumeLogRepositoryMR consumeLogRepositoryMR, IUserRepositoryJPA userRepositoryJPA) {
        this.redisStackManager = redisStackManager;
        this.treatmentMedicineService = treatmentMedicineService;
        this.consumeLogRepositoryMR = consumeLogRepositoryMR;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public HasPendency CheckIfHasPendency(int userId){
        String key = String.valueOf(userId);
        return new HasPendency(redisStackManager.KeyExists(key));
    }

    @Override
    public DayResume GetResumeOfDay(int userId){
        UserAccount user = userRepositoryJPA.getReferenceById(userId);
        DayResume resume = new DayResume();

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<MedicineConsumeLog> consumeLogList = consumeLogRepositoryMR.findByUsernameAndLogAtBetween(user.getLogin(), startOfDay, endOfDay).orElse(new ArrayList<>());
        List<MedicineCalendarDetailing> allMedicines = treatmentMedicineService.FindByDay(userId, LocalDateTime.now()).getMedicines();

        AddGlobalValues(resume, consumeLogList, allMedicines);
        AddTreatmentsResume(resume, consumeLogList, allMedicines);

        return resume;
    }

    private void AddTreatmentsResume(DayResume resume, List<MedicineConsumeLog> consumeLogList, List<MedicineCalendarDetailing> allMedicines){
        List<TreatmentResume> treatmentsResume = new ArrayList<>();

        Map<String, List<MedicineCalendarDetailing>> groupedByTreatmentId = allMedicines.stream()
                .collect(Collectors.groupingBy(MedicineCalendarDetailing::getTreatmentName));

        groupedByTreatmentId.forEach((treatmentName, detailingList) -> {
            TreatmentResume treatmentResume = new TreatmentResume(treatmentName);

            detailingList.forEach(detailing -> {
                MedicineResume medicineResume = new MedicineResume();
                medicineResume.setName(detailing.getMedicineName());
                medicineResume.setStatus(CaptureMedicineStatus(detailing.getMedicineName(), consumeLogList));

                treatmentResume.AddMedicine(medicineResume);
            });

            treatmentsResume.add(treatmentResume);
        });

        resume.setTreatments(treatmentsResume);
    }

    private String CaptureMedicineStatus(String medicineName, List<MedicineConsumeLog> consumeLogList){
        List<MedicineConsumeLog> targetMedicineLog = consumeLogList.stream().filter(m -> m.getMedicine().getName().equals(medicineName)).toList();

        if(targetMedicineLog.isEmpty()){
            return  "Normal";
        }

        boolean hasConsumed = !consumeLogList.stream().map(m -> m.getAction().equalsIgnoreCase("consumido")).toList().isEmpty();

        if(hasConsumed){
            return "Consumed";
        }

        boolean hasDelayed = !consumeLogList.stream().map(m -> m.getAction().equalsIgnoreCase("atrasado")).toList().isEmpty();

        if(hasDelayed){
            return "Delayed";
        }

        return "Normal";
    }

    private void AddGlobalValues(DayResume resume, List<MedicineConsumeLog> consumeLogList, List<MedicineCalendarDetailing> allMedicines){
        int total = allMedicines.size();
        resume.setTotal(total);

        int consumed = consumeLogList.stream().filter(m -> m.getAction().equalsIgnoreCase("consumido")).toList().size();
        resume.setConsumed(consumed);

        int delayed = consumeLogList.stream().filter(m -> m.getAction().equalsIgnoreCase("atrasado")).toList().size();
        resume.setDelayed(delayed);

        resume.setNormal(total - (delayed + consumed));
    }
}
