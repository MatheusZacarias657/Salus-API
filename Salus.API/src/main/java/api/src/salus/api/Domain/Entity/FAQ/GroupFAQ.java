package api.src.salus.api.Domain.Entity.FAQ;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "group_faq")
@Entity(name = "GroupFAQ")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class GroupFAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String Title;

    @Column(name = "Sub_Title")
    private String SubTitle;
}