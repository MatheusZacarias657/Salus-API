package bkd.src.salus.notificator.Application.Logger;

import bkd.src.salus.notificator.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.notificator.Domain.Entity.NoSQL.MedicineConsumeLog;
import bkd.src.salus.notificator.Domain.Interface.Application.ILogMedicine;
import bkd.src.salus.notificator.Repository.NoSQL.IMedicineConsumeLogRepositoryMR;
import bkd.src.salus.notificator.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogMedicine implements ILogMedicine {

    private final IMedicineConsumeLogRepositoryMR medicineConsumeLogRepositoryMR;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA;

    @Autowired
    public LogMedicine(IMedicineConsumeLogRepositoryMR medicineLogcRepositoryMR, IPatientRepositoryJPA patientRepositoryJPA, ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA) {
        this.medicineConsumeLogRepositoryMR = medicineLogcRepositoryMR;
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.treatmentMedicineRepositoryJPA = treatmentMedicineRepositoryJPA;
    }

    @Override
    public void LogConsume(int userId, int medicineId, String action){
        Patient patient = patientRepositoryJPA.findPatientByUserId(userId);
        TreatmentMedicine treatmentMedicine = treatmentMedicineRepositoryJPA.findMedicineTreatmentsByMedicineId(medicineId);
        MedicineConsumeLog consumeLog = new MedicineConsumeLog(patient, treatmentMedicine, action);
        medicineConsumeLogRepositoryMR.save(consumeLog);
    }
}
