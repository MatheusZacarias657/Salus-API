package bkd.src.salus.api.Domain.Interface.Application.LogAnswerableLastAccess;

import java.time.LocalDateTime;

public interface ILogAnswerableLastAccess {
    void LogAccess(int answerableId, int responsibleId);

    LocalDateTime CaptureLastAccess(int answerableId, int responsibleId);
}
