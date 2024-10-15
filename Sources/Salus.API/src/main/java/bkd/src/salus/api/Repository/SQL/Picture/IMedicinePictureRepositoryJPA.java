package bkd.src.salus.api.Repository.SQL.Picture;

import bkd.src.salus.api.Domain.Entity.SQL.Picture.MedicinePicture;
import bkd.src.salus.api.Domain.Entity.SQL.Picture.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IMedicinePictureRepositoryJPA extends JpaRepository<MedicinePicture, Integer> {

    @Query("""
            SELECT m
            FROM MedicinePicture m
            WHERE m.Medicine.Id = :medicineId
            AND m.Medicine.User.Id = :userId
            """)
    Optional<List<MedicinePicture>> findByMedicineIdAndUserId(int medicineId, int userId);

    @Query("""
            SELECT m
            FROM MedicinePicture m
            WHERE m.FileName = :fileName
            """)
    Optional<MedicinePicture> findByName(String fileName);
}
