package bkd.src.salus.communicator.Domain.Interface.Application.Notification;

import bkd.src.salus.communicator.Domain.DTO.Medicine.MedicineNotificationRequest;

public interface IInitializeNotificationHandler {
    void MessageProcess(MedicineNotificationRequest notification);
}
