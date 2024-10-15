package bkd.src.salus.api.Domain.DTO.File;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponseDTO {
    private byte[] Content;
    private String Name;
}
