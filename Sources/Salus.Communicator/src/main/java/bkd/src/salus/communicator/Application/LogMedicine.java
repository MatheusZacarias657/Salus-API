package bkd.src.salus.communicator.Application;

import bkd.src.salus.communicator.Domain.Entity.NoSQL.MedicineLog.MedicineConsumeLog;
import bkd.src.salus.communicator.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.communicator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.communicator.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.communicator.Domain.Interface.Application.ILogMedicine;
import bkd.src.salus.communicator.Repository.NoSQL.MedicineLog.IMedicineConsumeLogRepositoryMR;
import bkd.src.salus.communicator.Repository.SQL.IUserRepositoryJPA;
import bkd.src.salus.communicator.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.communicator.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogMedicine implements ILogMedicine {

    private final IMedicineConsumeLogRepositoryMR medicineConsumeLogRepositoryMR;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA;
    private final IUserRepositoryJPA userRepositoryJPA;

    @Autowired
    public LogMedicine(IMedicineConsumeLogRepositoryMR medicineLogcRepositoryMR, IPatientRepositoryJPA patientRepositoryJPA, ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA, IUserRepositoryJPA userRepositoryJPA) {
        this.medicineConsumeLogRepositoryMR = medicineLogcRepositoryMR;
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.treatmentMedicineRepositoryJPA = treatmentMedicineRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public void LogConsume(int userId, int medicineId, int treatmentId, String action){
        UserAccount user = userRepositoryJPA.findById(userId).get();
        Patient patient = patientRepositoryJPA.findPatientByUserId(userId);
        TreatmentMedicine treatmentMedicine = treatmentMedicineRepositoryJPA.findMedicineTreatmentByMedicineIdAndTreatmentId(medicineId, treatmentId);
        MedicineConsumeLog consumeLog = (patient == null) ? new MedicineConsumeLog(user, treatmentMedicine, action) : new MedicineConsumeLog(patient, treatmentMedicine, action);
        medicineConsumeLogRepositoryMR.save(consumeLog);
    }
}
