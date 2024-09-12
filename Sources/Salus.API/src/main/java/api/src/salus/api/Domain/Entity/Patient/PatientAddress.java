package api.src.salus.api.Domain.Entity.Patient;


import api.src.salus.api.Domain.DTO.Patient.Address.RegisterPatientAddressDTO;
import api.src.salus.api.Domain.DTO.Patient.Address.UpdatePatientAddressDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.RegisterPatientDataDTO;
import api.src.salus.api.Domain.DTO.Patient.Data.UpdatePatientDataDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patient_address")
@Entity(name = "PatientAddress")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class PatientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Patient_Id")
    private Patient Patient;

    private String Zipcode;
    private String Street;
    private String Neighborhood;
    private String City;
    private String State;

    public PatientAddress(RegisterPatientAddressDTO patientAddress, Patient patient){
        this.Patient = patient;
        this.Zipcode = patientAddress.getZipcode();
        this.Street = patientAddress.getStreet();
        this.Neighborhood = patientAddress.getNeighborhood();
        this.City = patientAddress.getCity();
        this.State = patientAddress.getState();
    }

    public void Update(UpdatePatientAddressDTO patientAddress){
        this.Zipcode = (patientAddress.getZipcode() != null) ? patientAddress.getZipcode() : this.Zipcode;
        this.Street = (patientAddress.getStreet() != null) ? patientAddress.getStreet() : this.Street;
        this.Neighborhood = (patientAddress.getNeighborhood() != null) ? patientAddress.getNeighborhood() : this.Neighborhood;
        this.City = (patientAddress.getCity() != null) ? patientAddress.getCity() : this.City;
        this.State = (patientAddress.getState() != null) ? patientAddress.getState() : this.State;
    }
}
