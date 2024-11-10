package bkd.src.salus.api.Application.Logger;

import bkd.src.salus.api.Domain.Entity.NoSQL.TreatmentRegisterLog.TreatmentMedicineRegister;
import bkd.src.salus.api.Domain.Entity.NoSQL.TreatmentRegisterLog.TreatmentRegisterLog;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogTreatmentRegister;
import bkd.src.salus.api.Repository.NoSQL.Mongo.TreatmentRegisterLog.ITreatmentRegisterLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogTreatment implements ILogTreatmentRegister {

    private final ITreatmentRegisterLogRepositoryMR treatmentRegisterLogRepositoryMR;
    private final IPatientRepositoryJPA patientRepositoryJPA;

    @Autowired
    public LogTreatment(ITreatmentRegisterLogRepositoryMR treatmentRegisterLogRepositoryMR, IPatientRepositoryJPA patientRepositoryJPA) {
        this.treatmentRegisterLogRepositoryMR = treatmentRegisterLogRepositoryMR;
        this.patientRepositoryJPA = patientRepositoryJPA;
    }

    @Override
    public void LogRegister(UserAccount user, Treatment treatment, List<TreatmentMedicine> treatmentMedicines){
        Patient patient = patientRepositoryJPA.findPatientByUserId(user.getId());

        TreatmentRegisterLog log = (patient != null)
                ? new TreatmentRegisterLog(treatment, patient)
                : new TreatmentRegisterLog(treatment, user);

        List<TreatmentMedicineRegister> medicineRegisters = treatmentMedicines.stream().map(TreatmentMedicineRegister::new).toList();
        log.AddMedicine(medicineRegisters);

        treatmentRegisterLogRepositoryMR.save(log);
    }
}
