package bkd.src.salus.api.Domain.DTO.Drawer;

import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
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
