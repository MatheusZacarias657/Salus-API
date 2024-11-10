package bkd.src.salus.api.Domain.Interface.Application.Logger;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;

public interface ILogBuyingMedicine {
    void LogBuying(UserAccount user, Medicine medicine);
}
