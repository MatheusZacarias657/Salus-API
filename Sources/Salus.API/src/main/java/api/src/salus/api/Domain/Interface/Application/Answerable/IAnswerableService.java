package api.src.salus.api.Domain.Interface.Application.Answerable;

import api.src.salus.api.Domain.DTO.Answerable.AnswarebleUserDTO;
import api.src.salus.api.Domain.DTO.Answerable.OtpDTO;

public interface IAnswerableService {
    OtpDTO CreateOtp(int id);

    AnswarebleUserDTO checkBeforeRegister(String otp);

    void RegisterAnswerable(String otp, int id);
}
