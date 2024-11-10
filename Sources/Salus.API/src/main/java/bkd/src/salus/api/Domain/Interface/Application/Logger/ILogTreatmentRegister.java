package bkd.src.salus.api.Domain.Interface.Application.Logger;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import bkd.src.salus.api.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;

import java.util.List;

public interface ILogTreatmentRegister {
    void LogRegister(UserAccount user, Treatment treatment, List<TreatmentMedicine> treatmentMedicines);
}
