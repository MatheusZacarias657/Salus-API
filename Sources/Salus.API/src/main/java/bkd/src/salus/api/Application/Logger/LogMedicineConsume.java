package bkd.src.salus.api.Application.Logger;

import bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume.MedicineConsumeLog;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogMedicineConsume;
import bkd.src.salus.api.Repository.NoSQL.Mongo.MedicineConsumeLog.IMedicineConsumeLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogMedicineConsume implements ILogMedicineConsume {

    private final IMedicineConsumeLogRepositoryMR medicineConsumeLogRepositoryMR;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA;
    private final IUserRepositoryJPA userRepositoryJPA;

    @Autowired
    public LogMedicineConsume(IMedicineConsumeLogRepositoryMR medicineLogcRepositoryMR, IPatientRepositoryJPA patientRepositoryJPA, ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA, IUserRepositoryJPA userRepositoryJPA) {
        this.medicineConsumeLogRepositoryMR = medicineLogcRepositoryMR;
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.treatmentMedicineRepositoryJPA = treatmentMedicineRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public void LogConsume(int userId, int medicineId, int treatmentId){
        UserAccount user = userRepositoryJPA.getReferenceById(userId);
        Patient patient = patientRepositoryJPA.findPatientByUserId(userId);
        TreatmentMedicine treatmentMedicine = treatmentMedicineRepositoryJPA.findMedicineTreatmentByMedicineIdAndTreatmentId(medicineId, treatmentId);
        MedicineConsumeLog consumeLog = (patient == null) ? new MedicineConsumeLog(user, treatmentMedicine, "Consumido") : new MedicineConsumeLog(patient, treatmentMedicine, "Consumido");
        medicineConsumeLogRepositoryMR.save(consumeLog);
    }
}
