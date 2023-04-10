package es.bifacia.bgg.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnectionManager {
	private static final String GET_METHOD = "GET";
	private static final int SUCCESS_STATUS = 200;
	private static final int RETRY_STATUS = 202;
	private static final int TOO_MANY_REQUESTS_STATUS = 429;

	public URLConnectionManager() {
		super();
	}

	/**
	 * Does a GET request.
	 * 
	 * @param stringURL URL to where to send the request.
	 * @return Response obtained from the request.
	 * @throws Exception
	 */
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
				if (status != RETRY_STATUS && status != TOO_MANY_REQUESTS_STATUS) {
					retry = false;
					if (status == SUCCESS_STATUS) {
						response = this.getResponse(connection);
					} else {
						System.out.println("Response code not expected: " + connection.getResponseCode());
					}
				} else {
					Thread.sleep(2000);
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
