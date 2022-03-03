package es.bifacia.bgg.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnectionManager {
	private static final String GET_METHOD = "GET";
	private static final int SUCCESS_STATUS = 200;
	private static final int RETRY_STATUS = 201;

	public String doGet(final String stringURL) throws Exception {
		String response = null;
		HttpURLConnection connection = null;
		try {
			boolean retry = true;
			while (retry) {
				URL url = new URL(stringURL);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET_METHOD);
				int status = connection.getResponseCode();
				if (status != RETRY_STATUS) {
					retry = false;
				} else if (status == SUCCESS_STATUS) {
					response = this.getResponse(connection);
				}
			}
		} catch (Exception ex) {
			throw new Exception("Error connecting to " + stringURL + ". \n" + ex.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	/**
	 * Gets the response from the HTTP connection.
	 * 
	 * @param connection Connection with the response.
	 * @return Content of the response.
	 * @throws Exception
	 */
	private String getResponse(final HttpURLConnection connection) throws Exception {
		String response = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			response = content.toString();
		}
		return response;
	}

}
