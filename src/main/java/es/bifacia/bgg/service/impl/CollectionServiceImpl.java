package es.bifacia.bgg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bifacia.bgg.bean.Game;
import es.bifacia.bgg.service.CollectionService;
import es.bifacia.bgg.service.XmlBggApiService;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private XmlBggApiService xmlBggApiService;

	/**
	 * Gets the games owned by a user without the expansions.
	 * 
	 * @param userName User name of the user we want to retrieve the owned games.
	 * @return Games owned by a user without the expansions.
	 * @throws Exception
	 */
	public List<Game> getUserOwnedGamesWithoutExpansions(final String userName) throws Exception {
		return xmlBggApiService.getUserOwnedGamesWithoutExpansions(userName);
	}
	
	/**
	 * Gets the list of voted games by a user.
	 * 
	 * @param userName User name of the user we want to obtain the voted games.
	 * @return List of voted games by a user.
	 * @throws Exception
	 */
	public List<Game> getUserVotedGames(final String userName) throws Exception {
		return this.xmlBggApiService.getUserVotedGames(userName);
	}
	
	/**
	 * Gets the list of want to play games for a user.
	 * 
	 * @param userName User name of the user we want to obtain the games he wants to
	 *                 play.
	 * @return List of games the user wants to play.
	 * @throws Exception
	 */
	public List<Game> getUserWantToPlayGames(final String userName) throws Exception {
		return this.xmlBggApiService.getUserWantToPlayGames(userName);
	}
	
	/**
	 * Gets the list of played games by a user.
	 * 
	 * @param userName User name of the user we want to obtain the played games.
	 * @return List of played games by a user.
	 * @throws Exception
	 */
	public List<Game> getUserPlayedGames(final String userName) throws Exception {
		return this.xmlBggApiService.getUserPlayedGames(userName);
	}

}
