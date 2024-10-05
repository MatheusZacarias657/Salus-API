package bkd.src.salus.api.Application.Service.Patient;

import bkd.src.salus.api.Domain.DTO.Patient.Address.PatientAddressModifierResponseDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Address.RegisterPatientAddressDTO;
import bkd.src.salus.api.Domain.DTO.Patient.Address.UpdatePatientAddressDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.PatientAddress;
import bkd.src.salus.api.Domain.Interface.Application.Patient.IPatientCrudService;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientAddressRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
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
