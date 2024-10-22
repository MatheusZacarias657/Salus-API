package bkd.src.salus.notificator.Application.Medicine;

import bkd.src.salus.notificator.Domain.DTO.Message.RequestWhatsappNotification;
import bkd.src.salus.notificator.Domain.DTO.Notification.AnswerableNotification;
import bkd.src.salus.notificator.Domain.DTO.Notification.MedicineNotificationRequest;
import bkd.src.salus.notificator.Domain.Entity.NoSQL.AnswerableNotificationLog;
import bkd.src.salus.notificator.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.notificator.Domain.Entity.SQL.Treatment.TreatmentMedicine;
import bkd.src.salus.notificator.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.notificator.Domain.Interface.Application.IAnswerableNotificationHandler;
import bkd.src.salus.notificator.Domain.Interface.Application.IRabbitMessageSender;
import bkd.src.salus.notificator.Domain.Interface.Repository.IRedisStackManager;
import bkd.src.salus.notificator.Repository.NoSQL.Mongo.IAnswerableNotificationLogRepositoryMR;
import bkd.src.salus.notificator.Repository.SQL.IUserRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.IWhatsTextRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.notificator.Repository.SQL.Treatment.ITreatmentMedicineRepositoryJPA;
import com.google.gson.Gson;
import org.graalvm.collections.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AnswerableNotificationHandler implements IAnswerableNotificationHandler {

    private final IRedisStackManager redisStackManager;
    private final Gson objectMap;
    private final IRabbitMessageSender rabbitMessageSender;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final IWhatsTextRepositoryJPA whatsTextRepositoryJPA;
    private final ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA;
    private final IAnswerableNotificationLogRepositoryMR answerableNotificationLogRepositoryMR;

    @Autowired
    public AnswerableNotificationHandler(IRedisStackManager redisStackManager, IRabbitMessageSender rabbitMessageSender, IUserRepositoryJPA userRepositoryJPA, IPatientRepositoryJPA patientRepositoryJPA, IWhatsTextRepositoryJPA whatsTextRepositoryJPA, ITreatmentMedicineRepositoryJPA treatmentMedicineRepositoryJPA, IAnswerableNotificationLogRepositoryMR answerableNotificationLogRepositoryMR) {
        this.redisStackManager = redisStackManager;
        this.rabbitMessageSender = rabbitMessageSender;
        this.userRepositoryJPA = userRepositoryJPA;
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.whatsTextRepositoryJPA = whatsTextRepositoryJPA;
        this.treatmentMedicineRepositoryJPA = treatmentMedicineRepositoryJPA;
        this.answerableNotificationLogRepositoryMR = answerableNotificationLogRepositoryMR;
        this.objectMap = new Gson();
    }

    @Override
    public void CheckAndSend(AnswerableNotification notification){
        String key = String.valueOf(notification.getUserId());
        List<String> targetQueue = redisStackManager.GetAllValues(key);

        for (int i=0; i< targetQueue.size(); i++){
            String jsonItem = targetQueue.get(i);
            MedicineNotificationRequest request = objectMap.fromJson(jsonItem, MedicineNotificationRequest.class);

            if(request.getUserId() == notification.getUserId()
               && request.getMedicineId() == notification.getMedicineId()){

                SendNotification(notification.getUserId(), request);
                targetQueue.remove(i);

                if(!targetQueue.isEmpty()){
                    redisStackManager.PushAllValues(key, targetQueue);
                }
                else {
                    redisStackManager.DeleteList(key);
                }
                break;
            }
        }
    }

    private void SendNotification(int userId, MedicineNotificationRequest medicineRequest){

        UserAccount user = userRepositoryJPA.findById(userId).get();

        if(user.getId() == user.getAnswerable().getId()){
            System.out.printf("Has no Answerable for user %s\n", user.getLogin());
            return;
        }

        Patient answerable = patientRepositoryJPA.findPatientByUserId(user.getAnswerable().getId());

        if(answerable == null || (answerable.getTelephone() == null || answerable.getTelephone().isEmpty())){
            System.out.printf("The Answerable %s for user %s doesn't fill the data to contact", user.getAnswerable().getLogin(), user.getLogin());
            return;
        }

        String text = whatsTextRepositoryJPA.findBySubject("NotificationAnswerable").getText();
        TreatmentMedicine medicine = treatmentMedicineRepositoryJPA.findMedicineTreatmentsByMedicineId(medicineRequest.getMedicineId());
        String medicineHour = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"));

        String fillText = String.format(text, medicineRequest.getUserName(), medicine.getMedicine().getName(), medicineHour);
        LogNotification(medicine, answerable, user, fillText);

        RequestWhatsappNotification request = new RequestWhatsappNotification(true, answerable.getTelephone(), fillText);
        String messageToSend = objectMap.toJson(request);
        rabbitMessageSender.SendMessageOnExchange(messageToSend, "whatsapp-notification-exchange");
    }

    private void LogNotification(TreatmentMedicine medicine, Patient answerable, UserAccount user, String text){
        Patient patient = patientRepositoryJPA.findPatientByUserId(user.getId());
        AnswerableNotificationLog notificationLog;

        if(patient == null){
            notificationLog = new AnswerableNotificationLog(user, answerable, medicine, text);
        }
        else {
            notificationLog = new AnswerableNotificationLog(patient, answerable, medicine, text);
        }

        answerableNotificationLogRepositoryMR.save(notificationLog);
    }
}
