package es.bifacia.bgg.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import es.bifacia.bgg.bean.Game;

public class XMLManager {
	private static final String ID_ATTRIBUTE = "objectid";
	private static final String ITEM_TAG = "item";
	private static final String NAME_TAG = "name";
	private static final String YEAR_TAG = "yearpublished";

	/**
	 * Parses the information of the games contained in the XML.
	 * 
	 * @param xmlContent String with the content of the games in XML format.
	 * @return List of games.
	 * @throws Exception
	 */
	public List<Game> parseGames(final String xmlContent) throws Exception {
		final List<Game> games = new ArrayList<>();
		Document doc = this.readXMLDocument(xmlContent);
		Element rootNode = doc.getRootElement();
		List<Element> list = rootNode.getChildren(ITEM_TAG);
		if (list == null || list.isEmpty()) {
			throw new Exception("No items where found for the request.");
		}
		for (Element gameElement : list) {
			try {
				final Game game = this.parseGame(gameElement);
				if (game != null) {
					games.add(game);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return games;
	}

	/**
	 * Parses a game information from the XML element provided.
	 * 
	 * @param gameElement Element of a XML with the information of a game.
	 * @return Object with the information of the game.
	 * @throws Exception
	 */
	private Game parseGame(final Element gameElement) throws Exception {
		Game game = null;
		if (gameElement != null) {
			final String name = gameElement.getChildText(NAME_TAG);
			final String stringID = gameElement.getAttributeValue(ID_ATTRIBUTE);
			int id = 0;
			try {
				id = Integer.parseInt(stringID);
			} catch (Exception ex) {
				throw new Exception("Error getting game ID. \n" + ex.getMessage());
			}
			if (name == null || name.isEmpty() || id < 1) {
				throw new Exception("Invalid name or ID for the game.");
			}
			game = new Game();
			game.setId(id);
			game.setName(name);
			final String stringYear = gameElement.getChildText(YEAR_TAG);
			Integer year = null;
			try {
				year = Integer.parseInt(stringYear);
			} catch (Exception ex) {
			}
			game.setYear(year);
		}
		return game;
	}

	/**
	 * Loads in a document the content of a XML.
	 * 
	 * @param xmlContent String with XML format.
	 * @return
	 */
	private Document readXMLDocument(final String xmlContent) throws Exception {
		Document document = null;
		try {
			SAXBuilder sax = new SAXBuilder();
			try (InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"))) {
				document = sax.build(inputStream);
			}
		} catch (Exception ex) {
			throw new Exception("Error creating JDOM document from string XML. \n" + ex.getMessage());
		}
		return document;
	}

}
