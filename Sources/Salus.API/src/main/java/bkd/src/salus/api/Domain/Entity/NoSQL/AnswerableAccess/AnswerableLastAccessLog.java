package bkd.src.salus.api.Domain.Entity.NoSQL.AnswerableAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "AnswerableLastAccess_Log")
@TypeAlias("AnswerableLastAccessLog")
public class AnswerableLastAccessLog {

    @Id
    private String id;

    private LastAccessUserDetailing Answerable;
    private LastAccessUserDetailing Patient;
    private LocalDateTime LogAt;

    public AnswerableLastAccessLog(LastAccessUserDetailing patient, LastAccessUserDetailing answerable){
        this.Answerable = answerable;
        this.Patient = patient;
        this.LogAt = LocalDateTime.now();
    }
}
