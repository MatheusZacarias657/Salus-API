package bkd.src.salus.api.Application.Service.Answerable;

import bkd.src.salus.api.Application.Utils.RandomGenerator;
import bkd.src.salus.api.Domain.DTO.Answerable.AnswarebleUserDTO;
import bkd.src.salus.api.Domain.DTO.Answerable.OtpDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Answerable.AnswerableOtp;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Answerable.IAnswerableService;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IFindProfilePicture;
import bkd.src.salus.api.Repository.SQL.Answerable.IAnswerableOtpRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerableService implements IAnswerableService {

    private final IAnswerableOtpRepositoryJPA otpRepository;
    private final IUserRepositoryJPA userRepository;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final IFindProfilePicture findProfilePicture;

    @Autowired
    public AnswerableService(IAnswerableOtpRepositoryJPA otpRepository, IUserRepositoryJPA userRepository, IPatientRepositoryJPA patientRepositoryJPA, IFindProfilePicture findProfilePicture){
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.findProfilePicture = findProfilePicture;
    }

    @Override
    public OtpDTO CreateOtp(int id){
        String otp = RandomGenerator.Otp();
        UserAccount user = userRepository.getReferenceById(id);
        AnswerableOtp answerableOtp = new AnswerableOtp(user, otp);
        otpRepository.save(answerableOtp);

        return new OtpDTO(answerableOtp.getOtp());
    }

    @Override
    public AnswarebleUserDTO checkBeforeRegister(String otp){
        UserAccount entity = otpRepository.findUserIdByOtp(otp);
        Patient patient = patientRepositoryJPA.findPatientByUserId(entity.getId());
        String profilePicture = findProfilePicture.FindProfilePictureName(entity.getId());

        return (patient == null) ? new AnswarebleUserDTO(entity, profilePicture) : new AnswarebleUserDTO(patient, profilePicture);
    }

    @Override
    public void RegisterAnswerable(String otp, int id){
        UserAccount patientEntity = userRepository.getReferenceById(id);
        UserAccount answerableEntity = otpRepository.findUserIdByOtp(otp);

        patientEntity.setAnswerable(answerableEntity);
        userRepository.save(patientEntity);

        AnswerableOtp answerableOtp = otpRepository.findOtpByText(otp);
        otpRepository.delete(answerableOtp);
    }
}
