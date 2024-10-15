package bkd.src.salus.api.Domain.Entity.SQL.Picture;

import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicine_picture")
@Entity(name = "MedicinePicture")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class MedicinePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Medicine_Id")
    private Medicine Medicine;

    @Column(name = "File_Name")
    private String FileName;

    @Column(name = "File_Data")
    private byte[] FileData;

    public MedicinePicture(Medicine medicine, String fileName, byte[] fileData){
        this.Medicine = medicine;
        this.FileData = fileData;
        this.FileName = fileName;
    }
}
