package bkd.src.salus.notificator.Application.Medicine;

import bkd.src.salus.notificator.Application.Utils.Converter;
import bkd.src.salus.notificator.Domain.DTO.Medicine.MedicineRequestNotification;
import bkd.src.salus.notificator.Domain.DTO.Notification.MedicineNotificationRequest;
import bkd.src.salus.notificator.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.notificator.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.notificator.Domain.Interface.Application.ILogMedicine;
import bkd.src.salus.notificator.Domain.Interface.Application.IMedicineNotificationManager;
import bkd.src.salus.notificator.Domain.Interface.Application.IObjectJsonConverter;
import bkd.src.salus.notificator.Domain.Interface.Application.IRabbitMessageSender;
import bkd.src.salus.notificator.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.Treatment.ITreatmentRepositoryJPA;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicineNotificationManager implements IMedicineNotificationManager {

    private final ITreatmentMedicineRepositoryJPA treatmentMedicineRepository;
    private final ITreatmentRepositoryJPA treatmentRepositoryJPA;
    private final IRabbitMessageSender rabbitMessageSender;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final Gson objectMap;
    private final ILogMedicine logMedicine;

    @Autowired
    public MedicineNotificationManager(ITreatmentMedicineRepositoryJPA treatmentMedicineRepository, ITreatmentRepositoryJPA treatmentRepositoryJPA, IRabbitMessageSender rabbitMessageSender, IPatientRepositoryJPA patientRepositoryJPA, IObjectJsonConverter objectJsonConverter, ILogMedicine logMedicine) {
        this.treatmentMedicineRepository = treatmentMedicineRepository;
        this.treatmentRepositoryJPA = treatmentRepositoryJPA;
        this.rabbitMessageSender = rabbitMessageSender;
        this.patientRepositoryJPA = patientRepositoryJPA;
        objectMap = objectJsonConverter.GetConverter();
        this.logMedicine = logMedicine;
    }

    @Override
    public void ScheduleTreatment(int treatmentId){

        Treatment treatment = treatmentRepositoryJPA.findByTreatmentId(treatmentId);
        List<TreatmentMedicine> treatmentMedicines = treatmentMedicineRepository.findMedicineTreatmentsByTreamentId(treatmentId);

        for(TreatmentMedicine treatmentMedicine : treatmentMedicines){
            if(CheckNotificationToSend(treatmentMedicine, treatment)){
                MedicineRequestNotification requestNotification = new MedicineRequestNotification(treatmentMedicine, treatment);

                rabbitMessageSender.SendMessageOnExchangeAsync(
                        objectMap.toJson(requestNotification),
                        "request-medicine-notification-exchange",
                        Converter.ConvertHoursToMilly(treatmentMedicine.getFrequency()));
            }
        }
    }

    private boolean HasEnoughToFourDays(float frequence, LocalDateTime treatmentEnd, float dosage, int storage){
        LocalDateTime dateLimit = LocalDateTime.now().plusDays(4);
        LocalDateTime finalDate = treatmentEnd.isBefore(dateLimit) ? treatmentEnd : dateLimit;

        Duration duration = Duration.between(LocalDateTime.now(), finalDate);
        long minutesRemaining = duration.toMinutes();
        long minutesInterval = Math.round(frequence * 60);

        long consumesNeeded = (minutesRemaining/minutesInterval);
        int quantityConsume = (int) (dosage * consumesNeeded);

        return (quantityConsume < storage);
    }

    @Override
    public boolean CheckNotificationToSend(TreatmentMedicine treatmentMedicine, Treatment treatment){

        if(treatment.isFinished()){
            System.out.printf("The treatment %d already ended\n", treatment.getId());
            return false;
        }

        if(treatmentMedicine.isFinished()){
            System.out.printf("The medicine consumption of id %d already ended\n", treatmentMedicine.getId());
            return false;
        }

        if (treatmentMedicine.getTreatmentEnd().isBefore(LocalDateTime.now())){
            System.out.printf("The deadline of treament alredy past for id %d\n", treatmentMedicine.getId());
            treatmentMedicine.FinishTreatment();
            treatmentMedicineRepository.save(treatmentMedicine);
            return false;
        }

        Medicine medicine = treatmentMedicine.getMedicine();

        if (treatmentMedicine.getDosage() > medicine.getStorageQuantity()){
            System.out.printf("Doesn't have enough to consume now this treatment medicine %d\n", treatmentMedicine.getId());
            //TODO: comunicar
        }

        if(HasEnoughToFourDays(treatmentMedicine.getFrequency(), treatmentMedicine.getTreatmentEnd(), treatmentMedicine.getDosage(), medicine.getStorageQuantity())){
            System.out.printf("Doesn't have enough to continue this treatment medicine %d\n", treatmentMedicine.getId());
            //TODO: comunicar
        }

        //TODO: supostamente está 0kk
        System.out.printf("The medicine %d is Ok to send\n", medicine.getId());
        logMedicine.LogConsume(medicine.getUser().getId(), medicine.getId(), "Agendado");

        return true;
    }

    @Override
    public MedicineNotificationRequest CreateMqttPayload(Treatment treatment, TreatmentMedicine treatmentMedicine){
        Patient patient = patientRepositoryJPA.findPatientByUserId(treatment.getUser().getId());
        String name = (patient == null || patient.getName().isEmpty()) ? treatment.getUser().getLogin() : patient.getName();
        return new MedicineNotificationRequest(
                treatmentMedicine.getMedicine().getHardwareId(),
                treatment.getUser().getId(),
                treatmentMedicine.getMedicine().getDrawerNumber(),
                name,
                treatmentMedicine.getMedicine().getId(),
                treatmentMedicine.getDosage()
        );
    }

    @Override
    public void ScheduleNextConsume(Treatment treatment, TreatmentMedicine treatmentMedicine){
        treatmentMedicine.getMedicine().DecreaseQuantity((int) treatmentMedicine.getDosage());

        System.out.printf("Scheduling nex consume of Medicine %d\n", treatmentMedicine.getMedicine().getId());
        if(CheckNotificationToSend(treatmentMedicine, treatment)){
            MedicineRequestNotification requestNotification = new MedicineRequestNotification(treatmentMedicine, treatment);

            rabbitMessageSender.SendMessageOnExchangeAsync(
                    objectMap.toJson(requestNotification),
                    "request-medicine-notification-exchange",
                    Converter.ConvertHoursToMilly(treatmentMedicine.getFrequency()));
        }
    }
}
