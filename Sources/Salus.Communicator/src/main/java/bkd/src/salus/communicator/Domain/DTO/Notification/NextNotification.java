package bkd.src.salus.communicator.Domain.DTO.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NextNotification {
    private String Message;
    private String Topic;
}
