package salus.component.ws.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import salus.component.ws.Domain.DTO.GenericMessage;

@MessageMapping("/hello")
@SendTo("/topic/greetings")
public class HealthController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GenericMessage greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return new GenericMessage("Working!");
    }
}
