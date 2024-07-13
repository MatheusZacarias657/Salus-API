package api.src.salus.api.Repository.User;

import org.springframework.data.jpa.repository.JpaRepository;
import api.src.salus.api.Domain.Entity.User;

public interface UserRepositoryJPA extends JpaRepository<User, Integer> {
    User FindByLogin(final String login);
}
