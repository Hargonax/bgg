package es.bifacia.bgg;

import es.bifacia.bgg.service.excel.impl.ExcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import es.bifacia.bgg.service.MainService;
import es.bifacia.bgg.utils.Users;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan({"es.bifacia.bgg"})
public class BggToolsApplication {
	final String[] USERS = new String[]{Users.ANMA, Users.ALMU, Users.ERIS, Users.GABRIEL_TRUJILLO, "mouseover", Users.RAXAR, Users.REUNER, "guifa79", "Edievale", "keroak"};
	final String[] USERS_EMPATADAS = new String[]{"odarumarf", "setropo", "drpanush", "f3_lix", "tabernario", "Emibunker", "magallope", "Satanuco", "pelexoi", "gorende", "Longuev", "Sindiforever"};

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(BggToolsApplication.class, args);
	}

	@Bean
	public void start() throws Exception {
//		mainService.showGamesPlayedNotVotedForAUser(Users.REUNER);
//		mainService.showGamesNotPlayedFromUserCollection(Users.REUNER, Users.DUN_DARACH);
//		mainService.showGamesAUserWantsToPlayFromUserCollection(Users.REUNER, Users.GABRIEL_TRUJILLO);
//		mainService.showGamesInWantToPlayByYear(Users.REUNER);
		Arrays.sort(USERS, String.CASE_INSENSITIVE_ORDER);
//		mainService.exportUsersCollectionToExcel(USERS, ExcelServiceImpl.GAMES_OWNERS_FILE_PATH);
		mainService.showGamesInUserWantToPlayInUsersCollectiveCollection(USERS, Users.REUNER);
//		mainService.showGamesPlayedEachYear(Users.REUNER);
		System.out.println("Execution finished");
	}

}
