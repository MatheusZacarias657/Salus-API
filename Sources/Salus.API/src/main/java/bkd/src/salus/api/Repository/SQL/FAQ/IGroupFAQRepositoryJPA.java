package bkd.src.salus.api.Repository.SQL.FAQ;

import bkd.src.salus.api.Domain.Entity.SQL.FAQ.GroupFAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupFAQRepositoryJPA extends JpaRepository<GroupFAQ, Integer> {
}
