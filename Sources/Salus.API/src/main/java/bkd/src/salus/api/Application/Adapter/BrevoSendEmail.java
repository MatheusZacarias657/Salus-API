package bkd.src.salus.api.Application.Adapter;

import bkd.src.salus.api.Domain.Adapter.EmailBrevoPayload;
import bkd.src.salus.api.Domain.Adapter.EmailContact;
import bkd.src.salus.api.Domain.Interface.Application.Adapter.IBrevoSendEmail;
import bkd.src.salus.api.Domain.Interface.Application.HttpFactory.IHttpFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class BrevoSendEmail implements IBrevoSendEmail {
    private final IHttpFactory httpFactory;

    @Value("${api.brevo.url}")
    private String urlBrevo;

    @Value("${api.brevo.url}")
    private String apiToken;

    @Value("${api.brevo.url}")
    private String email;

    @Value("${api.brevo.url}")
    private String name;

    @Autowired
    public BrevoSendEmail(IHttpFactory httpFactory) {
        this.httpFactory = httpFactory;
    }

    @Override
    public void SendEmail(String userEmail, Map<String, String> parameters, int templateId) throws Exception {
        URI url = httpFactory.buildUri(urlBrevo, "/v3/smtp/email");

        Map<String, String> headers = new HashMap<>() {{
            put("api-key", apiToken);
        }};

        EmailBrevoPayload body = new EmailBrevoPayload(
                new EmailContact(email, name),
                new ArrayList<EmailContact>(List.of(new EmailContact(userEmail, userEmail))),
                templateId,
                parameters
        );

        //String response = httpFactory.PostRequest(url, body, headers).thenApply(HttpResponse::body).join();
        int statusResponse = httpFactory.PostRequest(url, body, headers).get().statusCode();

        if(statusResponse != 200){
            throw new Exception(String.format("The Brevo API return a error: %i", statusResponse));
        }
    }
}
