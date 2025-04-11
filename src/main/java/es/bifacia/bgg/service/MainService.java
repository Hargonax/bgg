package es.bifacia.bgg.service;

public interface MainService {

	/**
	 * Shows all the games a user has not played from a user owned games.
	 * 
	 * @param userWithVotedGames User who has voted the games.
	 * @param collectionOwner    User owner of the collection of games.
	 * @throws Exception
	 */
	void showGamesNotPlayedFromUserCollection(final String userWithVotedGames, final String collectionOwner)
			throws Exception;

	/**
	 * Shows the games a user wants to play from a user collection.
	 * 
	 * @param userWantsToPlay User with the want to play list.
	 * @param collectionOwner User owner of the collection where we are going to
	 *                        seach the games.
	 * @throws Exception
	 */
	void showGamesAUserWantsToPlayFromUserCollection(final String userWantsToPlay, final String collectionOwner)
			throws Exception;

	/**
	 * Shows the games not voted from the played games of a user.
	 * 
	 * @param userName User we want to check the games not voted from the played
	 *                 list.
	 * @throws Exception
	 */
	void showGamesPlayedNotVotedForAUser(final String userName) throws Exception;
	
	/**
	 * Shows the games in the want to play of a user ordered by year.
	 * 
	 * @param userName User name of the user for which we are going to sort the want
	 *                 to play list.
	 * @throws Exception
	 */
	void showGamesInWantToPlayByYear(final String userName) throws Exception;

	/**
	 * Generates an Excel file with the collection of all the users passed in the array.
	 * @param users Array of users.
	 * @param usersCollectionExcelPath Path of the Excel file where we want to store the users collection.
	 * @throws Exception
	 */
	void exportUsersCollectionToExcel(final String[] users, final String usersCollectionExcelPath) throws Exception;

	/**
	 * Shows the games that a user wants to play from a group of collections from different users.
	 * @param users Users with the collective collection.
	 * @param user User we are taking the want to play list from.
	 * @throws Exception
	 */
	void showGamesInUserWantToPlayInUsersCollectiveCollection(final String[] users, final String user) throws Exception;

	/**
	 * Shows the games played by the user each year.
	 * @param user User who has played the games.
	 * @throws Exception
	 */
	void showGamesPlayedEachYear(final String user) throws Exception;

}
