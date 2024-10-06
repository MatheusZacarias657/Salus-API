package bkd.src.salus.communicator.Domain.DTO.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class BaseNotification <T>{
    private String Action;
    private T params;
}
