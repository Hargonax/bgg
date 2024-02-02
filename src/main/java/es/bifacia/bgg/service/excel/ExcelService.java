package es.bifacia.bgg.service.excel;

import es.bifacia.bgg.bean.Game;
import es.bifacia.bgg.bean.GameOwners;

import java.util.List;
import java.util.Map;

public interface ExcelService {

	/**
	 * Creates an Excel file with all the information of the games by its owners
	 * @param gamesOwnersMap Map with the information of the games owners.
	 * @param filePath Path of the file where we are storing the information.
	 * @throws Exception
	 */
	void createGamesOwnersExcel(final Map<Long, GameOwners> gamesOwnersMap, final String filePath) throws Exception;

}
