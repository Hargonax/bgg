package es.bifacia.bgg;

import es.bifacia.bgg.service.excel.impl.ExcelServiceImpl;
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
	final String[] users = new String[]{"almu_cali", Users.ANMA, Users.DIANA, "Eris3_14", Users.GABRIEL_TRUJILLO, "juanmacastillo", "llamaradas", "mattiuskas", Users.REUNER, Users.Raxar};

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(BggToolsApplication.class, args);
	}

	@Bean
	public void start() throws Exception {
//		mainService.showGamesPlayedNotVotedForAUser(Users.REUNER);
//		mainService.showGamesNotPlayedFromUserCollection(Users.REUNER, Users.Raxar);
//		mainService.showGamesAUserWantsToPlayFromUserCollection(Users.REUNER, Users.DUN_DARACH);
//		mainService.showGamesInWantToPlayByYear(Users.REUNER);
		mainService.exportUsersCollectionToExcel(users, ExcelServiceImpl.GAMES_OWNERS_FILE_PATH);
		System.out.println("Execution finished");
	}

}
