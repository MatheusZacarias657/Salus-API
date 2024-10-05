package bkd.src.salus.api.Domain.Entity.SQL.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "drawer")
@Entity(name = "Drawer")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Drawer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "Esp_Id")
    private String EspId;

    private String Name;

    @Column(name = "Number_of_Drawers")
    private int NumberOfDrawers;

    public Drawer(RegisterDrawerDTO register){
        this.EspId = register.getEspId();
        this.Name = register.getName();
        this.NumberOfDrawers = register.getNumberOfDrawers();
    }
}
