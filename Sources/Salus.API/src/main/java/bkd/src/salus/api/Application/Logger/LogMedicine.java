package bkd.src.salus.api.Application.Logger;

import bkd.src.salus.api.Domain.Entity.NoSQL.BuyingMedicineLog.BuyingMedicineLog;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Logger.ILogBuyingMedicine;
import bkd.src.salus.api.Repository.NoSQL.Mongo.BuyingMedicineLog.IBuyingMedicineLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogMedicine implements ILogBuyingMedicine {

    private final IBuyingMedicineLogRepositoryMR buyingMedicineLogRepositoryMR;
    private final IPatientRepositoryJPA patientRepositoryJPA;

    @Autowired
    public LogMedicine(IBuyingMedicineLogRepositoryMR buyingMedicineLogRepositoryMR, IPatientRepositoryJPA patientRepositoryJPA) {
        this.buyingMedicineLogRepositoryMR = buyingMedicineLogRepositoryMR;
        this.patientRepositoryJPA = patientRepositoryJPA;
    }

    @Override
    public void LogBuying(UserAccount user, Medicine medicine){
        Patient patient = patientRepositoryJPA.findPatientByUserId(user.getId());

        BuyingMedicineLog log = (patient != null)
                ? new BuyingMedicineLog(medicine, patient)
                : new BuyingMedicineLog(medicine, user);

        buyingMedicineLogRepositoryMR.save(log);
    }
}
