package bkd.src.salus.api.Domain.Interface.Application.Patient;

public interface IPatientCrudService<T, U, V> {
    T RegisterPatientContent(U patientRegister, int userId);

    T UpdatePatientContent(V patientUpdate, int userId);

    T GetPatientContent(int userId);
}
