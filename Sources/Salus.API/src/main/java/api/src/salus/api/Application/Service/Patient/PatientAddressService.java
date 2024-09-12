package api.src.salus.api.Application.Service.Patient;

import api.src.salus.api.Domain.DTO.Patient.Address.PatientAddressModifierResponseDTO;
import api.src.salus.api.Domain.DTO.Patient.Address.RegisterPatientAddressDTO;
import api.src.salus.api.Domain.DTO.Patient.Address.UpdatePatientAddressDTO;
import api.src.salus.api.Domain.Entity.Patient.Patient;
import api.src.salus.api.Domain.Entity.Patient.PatientAddress;
import api.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import api.src.salus.api.Repository.Patient.IPatientAddressRepositoryJPA;
import api.src.salus.api.Repository.Patient.IPatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientAddressService implements IPatientCrudService<PatientAddressModifierResponseDTO, RegisterPatientAddressDTO, UpdatePatientAddressDTO> {

    private final IPatientAddressRepositoryJPA patientAddressRepositoryJPA;
    private final IPatientRepositoryJPA patientRepositoryJPA;

    @Autowired
    public PatientAddressService(IPatientAddressRepositoryJPA patientAddressRepositoryJPA, IPatientRepositoryJPA patientRepositoryJPA) {
        this.patientAddressRepositoryJPA = patientAddressRepositoryJPA;
        this.patientRepositoryJPA = patientRepositoryJPA;
    }

    @Override
    public PatientAddressModifierResponseDTO RegisterPatientContent(RegisterPatientAddressDTO patientRegister, int userId) {
        Patient patientEntity = patientRepositoryJPA.findPatientByUserId(userId);
        PatientAddress entity = new PatientAddress(patientRegister, patientEntity);
        entity = patientAddressRepositoryJPA.save(entity);

        return new PatientAddressModifierResponseDTO(entity);
    }

    @Override
    public PatientAddressModifierResponseDTO UpdatePatientContent(UpdatePatientAddressDTO patientUpdate, int userId) {
        PatientAddress entity = patientAddressRepositoryJPA.findPatientAddressByUserId(userId);
        entity.Update(patientUpdate);
        entity = patientAddressRepositoryJPA.save(entity);

        return new PatientAddressModifierResponseDTO(entity);
    }

    @Override
    public PatientAddressModifierResponseDTO GetPatientContent(int userId) {
        PatientAddress entity = patientAddressRepositoryJPA.findPatientAddressByUserId(userId);

        return new PatientAddressModifierResponseDTO(entity);
    }
}
