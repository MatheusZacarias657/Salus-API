package bkd.src.salus.notificator.Domain.DTO.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestWhatsappNotification {
    private boolean IsNotification;
    private String UserNumber;
    private String Message;
}
