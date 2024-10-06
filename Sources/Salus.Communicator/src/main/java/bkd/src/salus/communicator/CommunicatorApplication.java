package bkd.src.salus.communicator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@EnableRabbit
@SpringBootApplication
public class CommunicatorApplication {

	public static void main(String[] args){
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(CommunicatorApplication.class, args);
    }
}
