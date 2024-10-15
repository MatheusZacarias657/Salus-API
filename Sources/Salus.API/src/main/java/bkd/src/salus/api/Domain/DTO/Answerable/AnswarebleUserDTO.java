package bkd.src.salus.api.Domain.DTO.Answerable;

import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswarebleUserDTO {
    private String Login;
    private String Name;
    private String ProfilePicture;

    @JsonIgnore
    private String PictureName;

    public AnswarebleUserDTO(UserAccount user, String pictureName){
        this.Login = user.getLogin();
        this.PictureName = pictureName;
    }

    public AnswarebleUserDTO(Patient patient, String pictureName){
        this.Login = patient.getUser().getLogin();
        this.Name = patient.getName();
        this.PictureName = pictureName;
    }
}
