package bkd.src.salus.api.Domain.DTO.Treatment;

import bkd.src.salus.api.Domain.Entity.SQL.Treatment.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailingTreatmentDTO {
    private int Id;
    private String User;
    private String Name;
    private String Importance;
    private ResumeDetailingTreatment Resume;

    public DetailingTreatmentDTO(Treatment treatment, ResumeDetailingTreatment resume){
        this.Id = treatment.getId();
        this.User = treatment.getUser().getLogin();
        this.Name = treatment.getName();
        this.Importance = treatment.getImportance().getName();

        this.Resume = resume;
    }
}
