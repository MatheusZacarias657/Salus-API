package bkd.src.salus.api.Domain.DTO.Answerable;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerablePatient {
    private String Name;
    private String Gender;
    private LocalDateTime Birthdate;
    private String Telephone;
    private LocalDateTime LastAccess;

    public AnswerablePatient (UserAccount user, LocalDateTime lastAccess){
        this.Name = user.getLogin();
        this.LastAccess = lastAccess;
    }

    public AnswerablePatient (Patient patient, LocalDateTime lastAccess){
        this.Name = patient.getName();
        this.Gender = patient.getGender();
        this.Birthdate = patient.getBirthdate();
        this.Telephone = patient.getTelephone();
        this.LastAccess = lastAccess;
    }
}
