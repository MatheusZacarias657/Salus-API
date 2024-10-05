package bkd.src.salus.api.Repository.SQL.Drawer;

import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDrawerRepositoryJPA extends JpaRepository<Drawer, Integer> {

    @Query("""
            SELECT d
            FROM Drawer d
            WHERE d.EspId = :espId
            """)
    Drawer findByEspId(String espId);
}
