package api.src.salus.api.Domain.Interface.Application.Patient;

import api.src.salus.api.Domain.DTO.Patient.Disease.DetailingPatientDiseaseDTO;
import api.src.salus.api.Domain.DTO.Patient.Disease.RegisterPatientDiseaseDTO;

import java.util.List;

public interface IPatientComponentService<T, U> {
    List<T> AddPatienComponent(List<U> registers, int userId);

    List<T> ListComponents(int userId);

    void DeleteComponent(int componentId, int userId);
}
