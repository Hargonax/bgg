package es.bifacia.bgg.utils;

public abstract class URLUtils {
	private static final String START_OF_PARAMETERS = "?";
	private static final String PARAMETERS_SEPARATOR = "&";

	/**
	 * Adds a list of parameters to a URL.
	 * 
	 * @param url        URL to be enriched.
	 * @param parameters List of parameters to add.
	 * @return URL enriched.
	 */
	public static String addParametersToURL(final String url, final String... parameters) {
		String newURL = url;
		if (parameters != null && parameters.length > 0) {
			newURL += START_OF_PARAMETERS;
			for (int i = 0; i < parameters.length; i++) {
				if (!newURL.endsWith(START_OF_PARAMETERS)) {
					newURL += PARAMETERS_SEPARATOR;
				}
				newURL += parameters[i];
			}
		}
		return newURL;
	}

}
