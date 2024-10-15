package bkd.src.salus.notificator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class NotificatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificatorApplication.class, args);
		System.out.println("AAAAAA oia o log");
	}

}
