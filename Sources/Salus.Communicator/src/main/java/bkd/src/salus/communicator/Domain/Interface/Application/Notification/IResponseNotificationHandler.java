package bkd.src.salus.communicator.Domain.Interface.Application.Notification;

import bkd.src.salus.communicator.Domain.DTO.Notification.NextNotification;

import java.util.List;

public interface IResponseNotificationHandler {
    List<NextNotification> MessageProcess(String notification, String topicResponse);
}
