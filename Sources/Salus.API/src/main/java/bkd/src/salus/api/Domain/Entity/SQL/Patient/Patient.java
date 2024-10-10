package bkd.src.salus.api.Domain.Entity.SQL.Patient;


import bkd.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "patient")
@Entity(name = "Patient")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Name;
    private LocalDateTime Birthdate;
    private String Telephone;
    private String Gender;

    public Patient(RegisterPatientDataDTO patientData, UserAccount user){
        this.Name = patientData.getName();
        this.User = user;
        this.Birthdate = patientData.getBirthdate();
        this.Telephone = patientData.getTelephone();
        this.Gender = patientData.getGender();
    }

    public void Update(UpdatePatientDataDTO patientData){
        this.Name = (patientData.getName() != null) ? patientData.getName() : this.Name;
        this.Birthdate = (patientData.getBirthdate() != null) ? patientData.getBirthdate() : this.Birthdate;
        this.Telephone = (patientData.getTelephone() != null) ? patientData.getTelephone() : this.Telephone;
        this.Gender = (patientData.getGender() != null) ? patientData.getGender() : this.Gender;
    }
}
