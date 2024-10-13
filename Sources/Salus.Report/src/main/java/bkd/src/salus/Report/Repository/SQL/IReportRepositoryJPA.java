package bkd.src.salus.Report.Repository.SQL;

import bkd.src.salus.Report.Domain.Entity.SQL.Report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IReportRepositoryJPA extends JpaRepository<Report, Integer> {

    @Query("""
            SELECT r
            FROM Report r
            WHERE r.Name = :reportName
            """)
    Report findByReportByName(String reportName);
}
