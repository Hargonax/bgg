package es.bifacia.bgg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bifacia.bgg.bean.Game;
import es.bifacia.bgg.service.CollectionService;
import es.bifacia.bgg.service.MainService;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	private CollectionService collectionService;

	/**
	 * Shows all the games a user has not played from a user owned games.
	 * 
	 * @param userWithVotedGames User who has voted the games.
	 * @param collectionOwner    User owner of the collection of games.
	 * @throws Exception
	 */
	public void showGamesNotPlayedFromUserCollection(final String userWithVotedGames, final String collectionOwner)
			throws Exception {
		final List<Game> collection = this.collectionService.getUserOwnedGamesWithoutExpansions(collectionOwner);
		final List<Game> votedGames = this.collectionService.getUserVotedGames(userWithVotedGames);
		final Map<Long, String> ownedGamesMap = this.gamesListToMap(collection);
		this.removeGamesInListFromMap(ownedGamesMap, votedGames);
		this.showGames(ownedGamesMap);
	}

	/**
	 * Shows the games a user wants to play from a user collection.
	 * 
	 * @param userWantsToPlay User with the want to play list.
	 * @param collectionOwner User owner of the collection where we are going to
	 *                        seach the games.
	 * @throws Exception
	 */
	public void showGamesAUserWantsToPlayFromUserCollection(final String userWantsToPlay, final String collectionOwner)
			throws Exception {
		final List<Game> collection = this.collectionService.getUserOwnedGamesWithoutExpansions(collectionOwner);
		final List<Game> wantToPlayGames = this.collectionService.getUserWantToPlayGames(userWantsToPlay);
		final Map<Long, String> ownedGamesMap = this.gamesListToMap(collection);
		final List<String> matchedGames = this.getMatchedGames(ownedGamesMap, wantToPlayGames);
		this.showGames(matchedGames);
	}

	/**
	 * Shows the games not voted from the played games of a user.
	 * 
	 * @param userName User we want to check the games not voted from the played
	 *                 list.
	 * @throws Exception
	 */
	public void showGamesPlayedNotVotedForAUser(final String userName) throws Exception {
		final List<Game> playedGames = this.collectionService.getUserPlayedGames(userName);
		final List<Game> votedGames = this.collectionService.getUserVotedGames(userName);
		final Map<Long, String> playedGamesMap = this.gamesListToMap(playedGames);
		this.removeGamesInListFromMap(playedGamesMap, votedGames);
		this.showGames(playedGamesMap);
	}

	/**
	 * Transforms a list of games in a map of games.
	 * 
	 * @param games List of games to transform.
	 * @return Map of games.
	 */
	private Map<Long, String> gamesListToMap(final List<Game> games) {
		final Map<Long, String> gamesMap = new HashMap<>();
		for (final Game game : games) {
			gamesMap.put(game.getId(), game.getName());
		}
		return gamesMap;
	}

	/**
	 * Removes from a map all the games contained in the list provided.
	 * 
	 * @param gamesMap Map of games.
	 * @param games    List of games.
	 */
	private void removeGamesInListFromMap(final Map<Long, String> gamesMap, final List<Game> games) {
		games.stream().forEach((game) -> {
			if (gamesMap.containsKey(game.getId())) {
				gamesMap.remove(game.getId());
			}
		});
	}

	/**
	 * Gets a list of games with the games that are included in the provided map and
	 * list of games.
	 * 
	 * @param gamesMap Map of games to match.
	 * @param games    List of games to match.
	 * @return List of matched games.
	 */
	private List<String> getMatchedGames(final Map<Long, String> gamesMap, final List<Game> games) {
		final List<String> matchedGames = new ArrayList<>();
		games.stream().forEach((game) -> {
			if (gamesMap.containsKey(game.getId())) {
				matchedGames.add(game.getName());
			}
		});
		return matchedGames;
	}

	/**
	 * Shows in the console the list of games.
	 * 
	 * @param gamesMap Map with the games to show.
	 */
	private void showGames(final Map<Long, String> gamesMap) {
		List<String> list = new ArrayList<>(gamesMap.values());
		this.showGames(list);
	}

	/**
	 * Shows in the console the list of games.
	 * 
	 * @param games List with the games to show.
	 */
	private void showGames(final List<String> games) {
		Collections.sort(games);
		games.stream().forEach((gameName) -> {
			System.out.println(gameName);
		});

	}

}
