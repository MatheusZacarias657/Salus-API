package bkd.src.salus.communicator.Repository.SQL;

import bkd.src.salus.communicator.Domain.Entity.SQL.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepositoryJPA extends JpaRepository<UserAccount, Integer> {
}

