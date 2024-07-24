package api.src.salus.api.Domain.Interface.Application.Adapter;

import java.util.Map;

public interface IBrevoSendEmail {
    void SendEmail(String userEmail, Map<String, String> parameters, int templateId) throws Exception;
}
