package bkd.src.salus.api.Domain.DTO.Importance;

import bkd.src.salus.api.Domain.Entity.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.Medicine.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingImportanceDTO {
    private int Id;
    private String Name;

    public DetailingImportanceDTO(Importance importance){
        this.Name = importance.getName();
        this.Id = importance.getId();
    }
}
