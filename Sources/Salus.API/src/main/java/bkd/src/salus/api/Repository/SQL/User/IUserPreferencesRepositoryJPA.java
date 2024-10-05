package bkd.src.salus.api.Repository.SQL.User;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserPreferencesRepositoryJPA extends JpaRepository<UserPreference, Integer> {

    @Query("""
            SELECT u
            FROM UserPreference u
            WHERE u.User.Id = :id
            """)
    UserPreference getUserPreferenceByUserId(int id);
}
