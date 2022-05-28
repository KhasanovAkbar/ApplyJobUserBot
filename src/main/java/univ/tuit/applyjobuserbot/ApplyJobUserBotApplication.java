package univ.tuit.applyjobuserbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApplyJobUserBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplyJobUserBotApplication.class, args);
    }



}
