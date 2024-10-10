package bkd.src.salus.notificator.Domain.Interface.Application;

import bkd.src.salus.notificator.Domain.DTO.Notification.MedicineNotificationRequest;
import bkd.src.salus.notificator.Domain.Entity.Treatment.Treatment;
import bkd.src.salus.notificator.Domain.Entity.Treatment.TreatmentMedicine;

public interface IMedicineNotificationManager {
    void ScheduleTreatment(int treatmentId);

    boolean CheckNotificationToSend(TreatmentMedicine treatmentMedicine, Treatment treatment);

    MedicineNotificationRequest CreateMqttPayload(Treatment treatment, TreatmentMedicine treatmentMedicine);

    void ScheduleNextConsume(Treatment treatment, TreatmentMedicine treatmentMedicine);
}
