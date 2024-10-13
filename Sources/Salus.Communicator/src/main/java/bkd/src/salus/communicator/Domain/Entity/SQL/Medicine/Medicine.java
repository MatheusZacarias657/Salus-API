package bkd.src.salus.communicator.Domain.Entity.SQL.Medicine;

import bkd.src.salus.communicator.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.communicator.Domain.Entity.SQL.Cataloging.Importance;
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

    public void DecreaseQuantity(int decrease){
        this.StorageQuantity -= decrease;
    }
}
