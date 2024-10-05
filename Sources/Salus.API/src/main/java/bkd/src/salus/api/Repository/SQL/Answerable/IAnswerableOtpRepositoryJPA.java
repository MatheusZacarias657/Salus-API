package bkd.src.salus.api.Repository.SQL.Answerable;

import bkd.src.salus.api.Domain.Entity.SQL.Answerable.AnswerableOtp;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
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
