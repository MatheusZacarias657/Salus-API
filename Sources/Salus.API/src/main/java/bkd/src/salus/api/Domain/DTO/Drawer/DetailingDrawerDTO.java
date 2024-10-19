package bkd.src.salus.api.Domain.DTO.Drawer;

import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailingDrawerDTO {
    private String HardwareId;
    private String Name;
    private String User;
    private List<String> Topics;

    public DetailingDrawerDTO(Drawer drawer, UserAccount user, List<String> topics){
        this.Name = drawer.getName();
        this.HardwareId = drawer.getHardwareId();
        this.User = user.getLogin();
        this.Topics = new ArrayList<>(topics);
    }
}
