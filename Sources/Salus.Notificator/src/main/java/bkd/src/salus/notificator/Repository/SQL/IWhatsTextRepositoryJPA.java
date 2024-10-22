package bkd.src.salus.notificator.Repository.SQL;

import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.notificator.Domain.Entity.SQL.WhatsappText.WhatsappText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IWhatsTextRepositoryJPA extends JpaRepository<WhatsappText, Integer> {

    @Query("""
            SELECT t
            FROM WhatsappText t
            WHERE t.Subject = :subject
            """)
    WhatsappText findBySubject(String subject);
}

