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
    private int Id;
    private String Name;
    private String Gender;
    private LocalDateTime Birthdate;
    private String Telephone;
    private LocalDateTime LastAccess;
    private String ProfilePicture;

    public AnswerablePatient (UserAccount user, LocalDateTime lastAccess, String profilePicture){
        this.Name = user.getLogin();
        this.Id = user.getId();
        this.LastAccess = lastAccess;
        this.ProfilePicture = profilePicture;
    }

    public AnswerablePatient (Patient patient, LocalDateTime lastAccess, String profilePicture){
        this.Id = patient.getUser().getId();
        this.Name = patient.getName();
        this.Gender = patient.getGender();
        this.Birthdate = patient.getBirthdate();
        this.Telephone = patient.getTelephone();
        this.LastAccess = lastAccess;
        this.ProfilePicture = profilePicture;
    }
}
