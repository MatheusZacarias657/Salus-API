package api.src.salus.api.Domain.Interface.Application.User;

public interface IUserPasswordService {
    void ChangePassword(int userId, String newPassword);
}
