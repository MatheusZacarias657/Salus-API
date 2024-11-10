package bkd.src.salus.notificator.Application.ScheduleNotification;

import bkd.src.salus.notificator.Application.Utils.Converter;
import bkd.src.salus.notificator.Domain.DTO.Notification.WaterNotificationDTO;
import bkd.src.salus.notificator.Domain.Interface.Application.IObjectJsonConverter;
import bkd.src.salus.notificator.Domain.Interface.Application.IRabbitMessageSender;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class WaterNotification {

    private final IRabbitMessageSender messageSender;
    private final Gson objectMap;

    @Autowired
    public WaterNotification(IRabbitMessageSender messageSender, IObjectJsonConverter objectJsonConverter) {
        this.messageSender = messageSender;
        objectMap = objectJsonConverter.GetConverter();
    }

    private void ScheduleNextMessage(String hardwareId){
        Random random = new Random();
        int randomTime = random.nextInt(150 - 120 + 1) + 120;

        LocalDateTime nextSend = LocalDateTime.now().plusMinutes(randomTime);
        LocalTime nextHour = nextSend.toLocalTime();

        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        if (!nextHour.isBefore(startTime) || nextHour.isAfter(endTime)) {
            LocalDateTime nextDayAt10AM = LocalDateTime.now().plusDays(1)
                                                            .withHour(10)
                                                            .withMinute(0)
                                                            .withSecond(0);

            int randomMinutes = random.nextInt(41);
            nextSend = nextDayAt10AM.plusMinutes(randomMinutes);
        }

        WaterNotificationDTO requestNotification = new WaterNotificationDTO(hardwareId);

        int millisDifference = (int) ChronoUnit.MILLIS.between(LocalDateTime.now(), nextSend);

        messageSender.SendMessageOnExchangeAsync(
                objectMap.toJson(requestNotification),
                "request-medicine-notification-exchange",
                millisDifference);
    }

    public void InitilizeNotification(){

    }
}