package es.bifacia.bgg.service;

import java.util.List;

import es.bifacia.bgg.bean.Game;

public interface XmlBggApiService {

	/**
	 * Gets the collection of a user without its expansion.
	 * 
	 * @param userName User name of the user we want to retrieve the collection.
	 * @return List of owned games by a user.
	 * @throws Exception
	 */
	List<Game> getUserOwnedGamesWithoutExpansions(final String userName) throws Exception;

	/**
	 * Gets the list of voted games by a user.
	 * 
	 * @param userName User name of the user we want to obtain the voted games.
	 * @return List of voted games by a user.
	 * @throws Exception
	 */
	List<Game> getUserVotedGames(final String userName) throws Exception;
	
	/**
	 * Gets the list of want to play games for a user.
	 * 
	 * @param userName User name of the user we want to obtain the games he wants to
	 *                 play.
	 * @return List of games the user wants to play.
	 * @throws Exception
	 */
	List<Game> getUserWantToPlayGames(final String userName) throws Exception;
	
	/**
	 * Gets the list of played games by a user.
	 * 
	 * @param userName User name of the user we want to obtain the played games.
	 * @return List of played games by a user.
	 * @throws Exception
	 */
	List<Game> getUserPlayedGames(final String userName) throws Exception;

}
