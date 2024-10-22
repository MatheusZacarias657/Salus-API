package bkd.src.salus.notificator.Domain.Interface.Application;

import bkd.src.salus.notificator.Domain.DTO.Notification.AnswerableNotification;

public interface IAnswerableNotificationHandler {
    void CheckAndSend(AnswerableNotification notification);
}
