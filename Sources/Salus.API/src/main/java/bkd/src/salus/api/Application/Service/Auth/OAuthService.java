package bkd.src.salus.api.Application.Service.Auth;

import bkd.src.salus.api.Application.Utils.RandomGenerator;
import bkd.src.salus.api.Domain.DTO.Auth.TokenResponse;
import bkd.src.salus.api.Domain.DTO.Auth.UserForgetPassword;
import bkd.src.salus.api.Domain.DTO.User.UserGenericDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Adapter.IBrevoSendEmail;
import bkd.src.salus.api.Domain.Interface.Application.Auth.IOAuthService;
import bkd.src.salus.api.Domain.Interface.Application.Auth.ITokenGenerate;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService implements IOAuthService {

    private final AuthenticationManager manager;
    private final ITokenGenerate tokenSevice;
    private final IBrevoSendEmail brevoSendEmail;
    private final IUserRepositoryJPA userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IPatientRepositoryJPA patientRepository;

    @Value("${api.brevo.reset-templete}")
    private int resetTemplate;

    @Autowired
    public OAuthService(AuthenticationManager manager, ITokenGenerate tokenSevice, IBrevoSendEmail brevoSendEmail, IUserRepositoryJPA userRepository, PasswordEncoder passwordEncoder, IPatientRepositoryJPA patientRepositoryJPA){
        this.manager = manager;
        this.tokenSevice = tokenSevice;
        this.brevoSendEmail = brevoSendEmail;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepositoryJPA;
    }

    @Override
    public TokenResponse Login(UserGenericDTO login) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());
        Authentication auth = manager.authenticate(authenticationToken);
        String token = tokenSevice.GenerateToken((User) auth.getPrincipal());
        UserAccount userAccount = userRepository.findByLogin(login.getLogin());
        Patient patient = patientRepository.findPatientByUserId(userAccount.getId());
        String name = (patient != null) ? patient.getName() : null;

        return new TokenResponse(token, userAccount.getLogin(), name);
    }

    @Override
    public boolean ForgetPassword(UserForgetPassword user) throws Exception {
        UserAccount entity = userRepository.findByLogin(user.getLogin());
        String newPassword = RandomGenerator.Password();

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity = userRepository.save(entity);

        Map<String, String> emailParameters = new HashMap<>() {{
            put("password", newPassword);
            put("user", user.getLogin());
        }};

        brevoSendEmail.SendEmail(entity.getLogin(), emailParameters, resetTemplate);

        return true;
    }
}
