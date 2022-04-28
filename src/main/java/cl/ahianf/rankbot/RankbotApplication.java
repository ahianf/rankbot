package cl.ahianf.rankbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RankbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankbotApplication.class, args);
	}

}
