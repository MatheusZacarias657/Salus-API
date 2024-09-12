package api.src.salus.api.Domain.DTO.Answerable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDTO {

    @NotBlank
    private String Otp;
}
