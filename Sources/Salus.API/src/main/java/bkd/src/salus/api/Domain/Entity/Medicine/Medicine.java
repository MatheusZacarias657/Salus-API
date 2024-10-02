package bkd.src.salus.api.Domain.Entity.Medicine;

import bkd.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import bkd.src.salus.api.Domain.Entity.Cataloging.Importance;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "medicine")
@Entity(name = "Medicine")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Type_Id")
    private MedicineType Type;

    @Column(name = "Storage_Quantity")
    private int StorageQuantity;

    @Column(name = "Expiration_Date")
    private LocalDateTime ExpirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Importance_Id")
    private Importance Importance; //TODO: controller

    private boolean Removed;

    @Column(name = "Drawer_Number")
    private int DrawerNumber;

    private float Price;

    public Medicine (RegisterMedicineDTO register, UserAccount user,
                     MedicineType type, Importance importance){

        this.Name = register.getName();
        this.StorageQuantity = register.getStorageQuantity();
        this.ExpirationDate = register.getExpirationDate();
        this.DrawerNumber = register.getDrawerNumber();

        this.User = user;
        this.Type = type;
        this.Price = register.getPrice();
        this.Importance = importance;
        this.Removed = false;
    }

    public void Remove(){
        this.Removed = true;
    }
}
