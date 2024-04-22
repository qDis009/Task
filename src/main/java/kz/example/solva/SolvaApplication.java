package kz.example.solva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolvaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolvaApplication.class, args);
	}

}
