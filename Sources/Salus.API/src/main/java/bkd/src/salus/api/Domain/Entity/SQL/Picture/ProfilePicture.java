package bkd.src.salus.api.Domain.Entity.SQL.Picture;

import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profile_picture")
@Entity(name = "ProfilePicture")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    @Column(name = "File_Name")
    private String FileName;

    @Column(name = "File_Data")
    private byte[] FileData;

    public ProfilePicture(UserAccount user, String fileName, byte[] content){
        this.User = user;
        this.FileData = content;
        this.FileName = fileName;
    }
}
