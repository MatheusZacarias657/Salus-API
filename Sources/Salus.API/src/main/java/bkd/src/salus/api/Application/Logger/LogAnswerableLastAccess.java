package bkd.src.salus.api.Application.Logger;

import bkd.src.salus.api.Domain.Entity.NoSQL.AnswerableAccess.AnswerableLastAccessLog;
import bkd.src.salus.api.Domain.Entity.NoSQL.AnswerableAccess.LastAccessUserDetailing;
import bkd.src.salus.api.Domain.Entity.SQL.Patient.Patient;
import bkd.src.salus.api.Domain.Interface.Application.LogAnswerableLastAccess.ILogAnswerableLastAccess;
import bkd.src.salus.api.Repository.NoSQL.Mongo.AnswerableLastAccess.IAnswerableLastAccessLogRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Patient.IPatientRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class LogAnswerableLastAccess implements ILogAnswerableLastAccess {

    private final IPatientRepositoryJPA patientRepositoryJPA;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final IAnswerableLastAccessLogRepositoryMR answerableLastAccessLogRepositoryMR;

    @Autowired
    public LogAnswerableLastAccess(IPatientRepositoryJPA patientRepositoryJPA, IUserRepositoryJPA userRepositoryJPA, IAnswerableLastAccessLogRepositoryMR answerableLastAccessLogRepositoryMR) {
        this.patientRepositoryJPA = patientRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
        this.answerableLastAccessLogRepositoryMR = answerableLastAccessLogRepositoryMR;
    }

    @Override
    public void LogAccess(int answerableId, int responsibleId){
        Patient answerablePatient = patientRepositoryJPA.findPatientByUserId(answerableId);
        LastAccessUserDetailing answerable = (answerablePatient == null) ?
                new LastAccessUserDetailing(userRepositoryJPA.findById(answerableId).get()) :
                new LastAccessUserDetailing(answerablePatient);

        Patient responsiblePatient = patientRepositoryJPA.findPatientByUserId(responsibleId);
        LastAccessUserDetailing responsible = (responsiblePatient == null) ?
                new LastAccessUserDetailing(userRepositoryJPA.findById(responsibleId).get()) :
                new LastAccessUserDetailing(responsiblePatient);

        AnswerableLastAccessLog lastAccess = new AnswerableLastAccessLog(responsible, answerable);
        answerableLastAccessLogRepositoryMR.save(lastAccess);
    }

    @Override
    public LocalDateTime CaptureLastAccess(int answerableId, int responsibleId){
        AnswerableLastAccessLog lastAccess = answerableLastAccessLogRepositoryMR.findMostRecentAccess(responsibleId, answerableId);

        return (lastAccess == null) ? null : lastAccess.getLogAt();
    }
}
