package api.src.salus.api.Application.Service.Answerable;

import api.src.salus.api.Application.Utils.RandomGenerator;
import api.src.salus.api.Domain.DTO.Answerable.AnswarebleUserDTO;
import api.src.salus.api.Domain.DTO.Answerable.OtpDTO;
import api.src.salus.api.Domain.Entity.Answerable.AnswerableOtp;
import api.src.salus.api.Domain.Entity.User.UserAccount;
import api.src.salus.api.Domain.Interface.Application.Answerable.IAnswerableService;
import api.src.salus.api.Repository.Answerable.IAnswerableOtpRepositoryJPA;
import api.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class AnswerableService implements IAnswerableService {

    private IAnswerableOtpRepositoryJPA otpRepository;
    private IUserRepositoryJPA userRepository;

    @Autowired
    public AnswerableService(IAnswerableOtpRepositoryJPA otpRepository, IUserRepositoryJPA userRepository){
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OtpDTO CreateOtp(int id){
        String otp = RandomGenerator.Otp();
        UserAccount user = userRepository.getReferenceById(id);
        AnswerableOtp answerableOtp = new AnswerableOtp(0, otp, user);
        otpRepository.save(answerableOtp);

        return new OtpDTO(answerableOtp.getOtp());
    }

    @Override
    public AnswarebleUserDTO checkBeforeRegister(String otp){
        UserAccount entity = otpRepository.findUserIdByOtp(otp);

        return new AnswarebleUserDTO(entity.getLogin());
    }

    @Override
    public void RegisterAnswerable(String otp, int id){
        UserAccount answerableEntity = userRepository.getReferenceById(id);
        UserAccount entity = otpRepository.findUserIdByOtp(otp);
        entity.setAnswerable(answerableEntity);
        userRepository.save(entity);
    }
}
