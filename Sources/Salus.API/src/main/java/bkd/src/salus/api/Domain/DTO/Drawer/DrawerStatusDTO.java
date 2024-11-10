package bkd.src.salus.api.Domain.DTO.Drawer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DrawerStatusDTO {
    private int Available;
    private int Total;
    private int Occupied;
}
