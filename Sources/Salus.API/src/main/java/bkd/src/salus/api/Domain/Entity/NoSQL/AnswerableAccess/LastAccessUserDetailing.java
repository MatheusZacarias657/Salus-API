package bkd.src.salus.api.Domain.Entity.NoSQL.AnswerableAccess;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastAccessUserDetailing{
    private String Name;
    private String User;
    private int Id;

    public LastAccessUserDetailing(Patient patient){
        this.Name = patient.getName();
        this.User = patient.getUser().getLogin();
        this.Id = patient.getUser().getId();
    }

    public LastAccessUserDetailing(UserAccount user){
        this.Name = user.getLogin();
        this.User = user.getLogin();
        this.Id = user.getId();
    }
}
