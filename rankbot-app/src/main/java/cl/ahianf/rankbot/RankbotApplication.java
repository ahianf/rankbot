/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RankbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankbotApplication.class, args);
    }
}
