package bkd.src.salus.api.Repository.SQL.Picture;

import bkd.src.salus.api.Domain.Entity.SQL.Picture.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IProfilePictureRepositoryJPA extends JpaRepository<ProfilePicture, Integer> {

    @Query("""
            SELECT p
            FROM ProfilePicture p
            WHERE p.User.Id = :userId
            """)
    Optional<ProfilePicture> findByUserId(int userId);

    @Query("""
            SELECT p
            FROM ProfilePicture p
            WHERE p.FileName = :fileName
            """)
    Optional<ProfilePicture> findByName(String fileName);
}
