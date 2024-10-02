package bkd.src.salus.api.Repository.FAQ;

import bkd.src.salus.api.Domain.Entity.FAQ.GroupFAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupFAQRepositoryJPA extends JpaRepository<GroupFAQ, Integer> {
}
