package bkd.src.salus.api.Domain.Entity.FAQ;

import bkd.src.salus.api.Domain.Entity.Cataloging.Importance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "faq")
@Entity(name = "FAQ")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_Id")
    private GroupFAQ Group;

    private String Title;
    private String Text;
}