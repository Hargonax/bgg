package es.bifacia.bgg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.bifacia.bgg.bean.GameOwners;
import es.bifacia.bgg.service.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bifacia.bgg.bean.Game;
import es.bifacia.bgg.service.CollectionService;
import es.bifacia.bgg.service.MainService;

@Service
public class MainServiceImpl implements MainService {
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String RESET = "\033[0m";

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private ExcelService excelService;

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
		final Map<Long, Game> ownedGamesMap = this.gamesListToMap(collection);
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
		final Map<Long, Game> ownedGamesMap = this.gamesListToMap(collection);
		final List<Game> matchedGames = this.getMatchedGames(ownedGamesMap, wantToPlayGames);
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
		final Map<Long, Game> playedGamesMap = this.gamesListToMap(playedGames);
		this.removeGamesInListFromMap(playedGamesMap, votedGames);
		this.showGames(playedGamesMap);
	}

	/**
	 * Shows the games in the want to play of a user ordered by year.
	 * 
	 * @param userName User name of the user for which we are going to sort the want
	 *                 to play list.
	 * @throws Exception
	 */
	public void showGamesInWantToPlayByYear(final String userName) throws Exception {
		final List<Game> wantToPlayGames = this.collectionService.getUserWantToPlayGames(userName);
		final Map<Integer, List<Game>> gamesByYear = this.createMapOfGamesByPublishedYear(wantToPlayGames);
		for (int i = -1; i < 2035; i++) {
			if (gamesByYear.containsKey(i)) {
				final List<Game> games = gamesByYear.get(i);
				if (i == -1) {
					System.out.println(RED_BOLD + "Año desconocido (" + games.size() + ")" + RESET);
				} else {
					System.out.println(RED_BOLD + i + "(" + games.size() + ")" + RESET);
				}
				this.showGames(games);
			}
		}
	}

	/**
	 * Create a map of games separated by publication year.
	 * 
	 * @param games List of games to sort.
	 * @return Map of games by year.
	 */
	private Map<Integer, List<Game>> createMapOfGamesByPublishedYear(final List<Game> games) {
		final Map<Integer, List<Game>> map = new HashMap<>();
		if (games != null && !games.isEmpty()) {
			games.stream().forEach((game) -> {
				int year = -1;
				if (game.getYear() != null) {
					year = game.getYear();
				}
				List<Game> yearGames = null;
				if (map.containsKey(year)) {
					yearGames = map.get(year);
				} else {
					yearGames = new ArrayList<>();
					map.put(year, yearGames);
				}
				yearGames.add(game);
			});
		}
		return map;
	}

	/**
	 * Transforms a list of games in a map of games.
	 * 
	 * @param games List of games to transform.
	 * @return Map of games.
	 */
	private Map<Long, Game> gamesListToMap(final List<Game> games) {
		final Map<Long, Game> gamesMap = new HashMap<>();
		for (final Game game : games) {
			gamesMap.put(game.getId(), game);
		}
		return gamesMap;
	}

	/**
	 * Removes from a map all the games contained in the list provided.
	 * 
	 * @param gamesMap Map of games.
	 * @param games    List of games.
	 */
	private void removeGamesInListFromMap(final Map<Long, Game> gamesMap, final List<Game> games) {
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
	private List<Game> getMatchedGames(final Map<Long, Game> gamesMap, final List<Game> games) {
		final List<Game> matchedGames = new ArrayList<>();
		games.stream().forEach((game) -> {
			if (gamesMap.containsKey(game.getId())) {
				matchedGames.add(game);
			}
		});
		return matchedGames;
	}

	/**
	 * Shows in the console the list of games.
	 * 
	 * @param gamesMap Map with the games to show.
	 */
	private void showGames(final Map<Long, Game> gamesMap) {
		List<Game> list = new ArrayList<>(gamesMap.values());
		this.showGames(list);
	}

	/**
	 * Shows in the console the list of games.
	 * 
	 * @param games List with the games to show.
	 */
	private void showGames(final List<Game> games) {
		Collections.sort(games);
		games.stream().forEach((game) -> {
			System.out.println(game.getName());
		});
		System.out.println();
	}

	/**
	 * Generates an Excel file with the collection of all the users passed in the array.
	 * @param users Array of users.
	 * @param usersCollectionExcelPath Path of the Excel file where we want to store the users collection.
	 * @throws Exception
	 */
	public void exportUsersCollectionToExcel(final String[] users, final String usersCollectionExcelPath) throws Exception {
		final Map<Long, GameOwners> gamesOwnersMap = getGamesOwnersMap(users);
		excelService.createGamesOwnersExcel(gamesOwnersMap, usersCollectionExcelPath);
	}

	private Map<Long, GameOwners> getGamesOwnersMap(final String[] users) throws Exception {
		final Map<Long, GameOwners> gamesOwnersMap = new HashMap<>();
		for (int i = 0; i < users.length; i++) {
			final String user = users[i];
			final List<Game> collection = collectionService.getUserOwnedGamesWithoutExpansions(user);
			if (collection != null && !collection.isEmpty()) {
				addUserCollectionsToGamesOwnersMap(collection, gamesOwnersMap, user);
				System.out.println(user + " collection added.");
			}
		}
		return gamesOwnersMap;
	}

	private void addUserCollectionsToGamesOwnersMap(List<Game> collection, final Map<Long, GameOwners> gamesOwnersMap, final String owner) {
		collection.forEach(g -> {
			final long gameID = g.getId();
			if (gamesOwnersMap.containsKey(gameID)) {
				final List<String> owners = gamesOwnersMap.get(gameID).getOwners();
				owners.add(owner);
			} else {
				final List<String> owners = new ArrayList<>();
				owners.add(owner);
				gamesOwnersMap.put(gameID, new GameOwners(g, owners));
			}
		});
	}

	/**
	 * Shows the games that a user wants to play from a group of collections from different users.
	 * @param users Users with the collective collection.
	 * @param user User we are taking the want to play list from.
	 * @throws Exception
	 */
	@Override
	public void showGamesInUserWantToPlayInUsersCollectiveCollection(final String[] users, final String user) throws Exception {
		final Map<Long, GameOwners> gamesOwnersMap = getGamesOwnersMap(users);
		final List<Game> wantToPlayGames = this.collectionService.getUserWantToPlayGames(user);
		wantToPlayGames.forEach(g -> {
			if (gamesOwnersMap.containsKey(g.getId())) {
				String ownersAsString = "";
				final List<String> owners = gamesOwnersMap.get(g.getId()).getOwners();
				for (final String owner : owners) {
					if (!ownersAsString.isEmpty()) {
						ownersAsString += ", ";
					}
					ownersAsString += owner;
				}
				System.out.println(g.getName() + "   -    ");
			}
		});
	}

}
