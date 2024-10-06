package bkd.src.salus.api.Repository.SQL.Drawer;

import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDrawerRepositoryJPA extends JpaRepository<Drawer, Integer> {

    @Query("""
            SELECT d
            FROM Drawer d
            WHERE d.HardwareId = :hardwareId
            """)
    Drawer findByEspId(String hardwareId);

    @Query("""
            SELECT d
            FROM Drawer d
            JOIN DrawerGroup dg ON d.Id = dg.Drawer.Id
            WHERE dg.User.Id = :userId
            """)
    List<Drawer> findByUserId(int userId);
}
