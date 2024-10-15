package bkd.src.salus.api.Domain.Entity.SQL.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Table(name = "medicine")
@Entity(name = "Medicine")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
@SQLRestriction("Removed = false")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Type_Id")
    private MedicineType Type;

    @Column(name = "Storage_Quantity")
    private int StorageQuantity;

    @Column(name = "Expiration_Date")
    private LocalDateTime ExpirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Importance_Id")
    private Importance Importance;

    private boolean Removed;

    @Column(name = "Drawer_Number")
    private int DrawerNumber;

    @Column(name = "Hardware_Id")
    private String HardwareId;

    private float Price;

    public Medicine (RegisterMedicineDTO register, UserAccount user,
                     MedicineType type, Importance importance){

        this.Name = register.getName();
        this.StorageQuantity = register.getStorageQuantity();
        this.ExpirationDate = register.getExpirationDate();

        this.User = user;
        this.Type = type;
        this.Importance = importance;

        this.Price = register.getPrice();
        this.DrawerNumber = register.getDrawerNumber();
        this.HardwareId = register.getHardwareId();

        this.Removed = false;
    }

    public void Decrement(int quantity){
        this.StorageQuantity -=quantity;
    }

    public void Remove(){
        this.Removed = true;
    }
}
