package es.bifacia.bgg.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import es.bifacia.bgg.bean.Game;
import es.bifacia.bgg.manager.URLConnectionManager;
import es.bifacia.bgg.manager.XMLManager;
import es.bifacia.bgg.service.XmlBggApiService;
import es.bifacia.bgg.utils.URLUtils;

@Service
public class XmlBggApiServiceImpl implements XmlBggApiService {
	private static final String API_URL = "https://boardgamegeek.com/xmlapi2/collection";
	private static final String USER_NAME_PARAMETER = "username=";
	private static final String OWNED_GAMES_PARAMETER = "own=1";
	private static final String RATED_PARAMETER = "rated=1";
	private static final String WANT_TO_PLAY_PARAMETER = "wanttoplay=1";
	private static final String EXCLUDE_EXPANSIONS_PARAMETER = "excludesubtype=boardgameexpansion";

	/**
	 * Gets the collection of a user without its expansion.
	 * 
	 * @param userName User name of the user we want to retrieve the collection.
	 * @return List of owned games by a user.
	 * @throws Exception
	 */
	public List<Game> getUserOwnedGamesWithoutExpansions(final String userName) throws Exception {
		List<Game> games = null;
		final XMLManager xmlManager = new XMLManager();
		try {
			final URLConnectionManager connectionManager = new URLConnectionManager();
			final String url = URLUtils.addParametersToURL(API_URL, USER_NAME_PARAMETER + userName,
					OWNED_GAMES_PARAMETER, EXCLUDE_EXPANSIONS_PARAMETER);
			final String response = connectionManager.doGet(url);
			games = xmlManager.parseGames(response);
		} catch (Exception ex) {
			throw new Exception(
					"Error obtainer user " + userName + " collection without expansions. \n" + ex.getMessage());
		}
		return games;
	}

	/**
	 * Gets the list of voted games by a user.
	 * 
	 * @param userName User name of the user we want to obtain the voted games.
	 * @return List of voted games by a user.
	 * @throws Exception
	 */
	public List<Game> getUserVotedGames(final String userName) throws Exception {
		List<Game> games = null;
		final XMLManager xmlManager = new XMLManager();
		try {
			final URLConnectionManager connectionManager = new URLConnectionManager();
			final String url = URLUtils.addParametersToURL(API_URL, USER_NAME_PARAMETER + userName, RATED_PARAMETER);
			final String response = connectionManager.doGet(url);
			games = xmlManager.parseGames(response);
		} catch (Exception ex) {
			throw new Exception(
					"Error obtainer user " + userName + " collection without expansions. \n" + ex.getMessage());
		}
		return games;
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
		List<Game> games = null;
		final XMLManager xmlManager = new XMLManager();
		try {
			final URLConnectionManager connectionManager = new URLConnectionManager();
			final String url = URLUtils.addParametersToURL(API_URL, USER_NAME_PARAMETER + userName,
					WANT_TO_PLAY_PARAMETER);
			final String response = connectionManager.doGet(url);
			games = xmlManager.parseGames(response);
		} catch (Exception ex) {
			throw new Exception("Error obtainer user " + userName + " list of want to play games without expansions. \n"
					+ ex.getMessage());
		}
		return games;
	}

}
