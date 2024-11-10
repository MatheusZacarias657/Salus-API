package bkd.src.salus.notificator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class NotificatorApplication {

	public static void main(String[] args) {
		System.out.printf("The user %s doesn't consume the medicine %d,\n now user %s will be notified\n", "alan.roza@gmail.com", 1, "matheus.zacarias@gmail.com");
		SpringApplication.run(NotificatorApplication.class, args);
	}
}
