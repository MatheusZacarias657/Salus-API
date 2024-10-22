package bkd.src.salus.notificator.Repository.SQL;

import bkd.src.salus.notificator.Domain.Entity.SQL.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepositoryJPA extends JpaRepository<UserAccount, Integer> {
}

