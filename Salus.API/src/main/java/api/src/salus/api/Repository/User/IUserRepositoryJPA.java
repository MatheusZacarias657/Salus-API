package api.src.salus.api.Repository.User;

import org.springframework.data.jpa.repository.JpaRepository;
import api.src.salus.api.Domain.Entity.UserAccount;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepositoryJPA extends JpaRepository<UserAccount, Integer> {

    @Query("""
            SELECT u
            FROM User u
            WHERE u.Login = :login
            """)
    UserAccount findByLogin(String login);

    @Query("""
            SELECT u.Answerable.Id
            FROM User u
            WHERE u.Id = :id
            """)
    int getAnswerableIdById(int id);
}
