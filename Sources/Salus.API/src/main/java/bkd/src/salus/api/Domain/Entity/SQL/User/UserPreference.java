package bkd.src.salus.api.Domain.Entity.SQL.User;

import bkd.src.salus.api.Domain.DTO.User.UserPreferenceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "user_preferences")
@Entity(name = "UserPreference")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private UserAccount User;

    private String Typography;
    private boolean EnableStatistics;

    public void UpdateData(UserPreferenceDTO userPreference){
        this.EnableStatistics = userPreference.isEnableStatistics(); //TODO: Validar esse ponto de atualização
        this.Typography = (userPreference.getTypography() != null) ? userPreference.getTypography() : this.Typography;
    }
}