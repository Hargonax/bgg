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

}
