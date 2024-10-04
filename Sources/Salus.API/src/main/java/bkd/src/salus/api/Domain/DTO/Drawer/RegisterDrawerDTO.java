package bkd.src.salus.api.Domain.DTO.Drawer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDrawerDTO {

    @NotBlank
    private String Name;

    @NotBlank
    private String EspId;

    @NotNull
    private int NumberOfDrawers;
}
