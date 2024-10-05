package bkd.src.salus.api.Domain.DTO.Importance;

import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
