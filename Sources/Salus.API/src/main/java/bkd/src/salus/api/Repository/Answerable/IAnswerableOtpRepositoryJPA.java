package bkd.src.salus.api.Repository.Answerable;

import bkd.src.salus.api.Domain.Entity.Answerable.AnswerableOtp;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAnswerableOtpRepositoryJPA extends JpaRepository<AnswerableOtp, Integer> {

    @Query("""
            SELECT a.User
            FROM AnswerableOtp a
            WHERE a.Otp = :otp
            """)
    UserAccount findUserIdByOtp(String otp);
}
