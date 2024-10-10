package bkd.src.salus.api.Domain.Entity.SQL.Drawer;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "drawer_group")
@Entity(name = "DrawerGroup")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class DrawerGroup implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Drawer_Id")
    private Drawer Drawer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Owner_Id")
    private UserAccount Owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    public DrawerGroup(Drawer drawer, UserAccount owner){
        this.Drawer = drawer;
        this.Owner = owner;
    }

    public void AddUser(UserAccount user){
        this.User = user;
    }

    @Override
    public DrawerGroup clone() throws CloneNotSupportedException {
        return (DrawerGroup) super.clone();
    }
}
