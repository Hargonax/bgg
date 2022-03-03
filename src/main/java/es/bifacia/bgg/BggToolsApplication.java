package es.bifacia.bgg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import es.bifacia.bgg.service.MainService;
import es.bifacia.bgg.utils.Users;

@SpringBootApplication
@ComponentScan({"es.bifacia.bgg"})
public class BggToolsApplication {

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(BggToolsApplication.class, args);
	}

	@Bean
	public void start() throws Exception {
//		this.mainService.showGamesNotPlayedFromUserCollection(Users.REUNER, Users.ANMA);
		this.mainService.showGamesAUserWantsToPlayFromUserCollection(Users.RUBA, Users.REUNER);
	}

}
