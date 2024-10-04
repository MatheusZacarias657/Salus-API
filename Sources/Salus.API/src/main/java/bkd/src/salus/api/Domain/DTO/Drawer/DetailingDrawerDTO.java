package bkd.src.salus.api.Domain.DTO.Drawer;

import bkd.src.salus.api.Domain.Entity.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingDrawerDTO {
    private int Id;
    private String EspId;
    private String Name;
    private String User;

    public DetailingDrawerDTO(Drawer drawer, UserAccount user){
        this.Name = drawer.getName();
        this.Id = drawer.getId();
        this.EspId = drawer.getEspId();
        this.User = user.getLogin();
    }
}
