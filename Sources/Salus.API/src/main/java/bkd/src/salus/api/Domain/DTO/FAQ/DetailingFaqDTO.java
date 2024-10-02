package bkd.src.salus.api.Domain.DTO.FAQ;

import bkd.src.salus.api.Domain.Entity.FAQ.FAQ;
import bkd.src.salus.api.Domain.Entity.FAQ.GroupFAQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingFaqDTO {
    private String Title;
    private String Content;

    public DetailingFaqDTO (GroupFAQ group){
        this.Title = group.getTitle();
        this.Content = group.getSubTitle();
    }

    public DetailingFaqDTO (FAQ faq){
        this.Title = faq.getTitle();
        this.Content = faq.getText();
    }
}
