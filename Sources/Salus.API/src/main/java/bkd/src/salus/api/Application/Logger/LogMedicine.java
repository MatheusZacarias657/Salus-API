package bkd.src.salus.api.Application.Logger;

import bkd.src.salus.api.Domain.Entity.NoSQL.MedicineConsume.MedicineConsumeLog;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogMedicine;
import bkd.src.salus.api.Repository.NoSQL.MedicineLog.IMedicineConsumeLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
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
    public void LogConsume(int userId, int medicineId){
        Patient patient = patientRepositoryJPA.findPatientByUserId(userId);
        TreatmentMedicine treatmentMedicine = treatmentMedicineRepositoryJPA.findMedicineTreatmentsByMedicineId(medicineId);
        MedicineConsumeLog consumeLog = new MedicineConsumeLog(patient, treatmentMedicine, "Consumido");
        medicineConsumeLogRepositoryMR.save(consumeLog);
    }
}
