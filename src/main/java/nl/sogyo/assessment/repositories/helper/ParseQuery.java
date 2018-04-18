package nl.sogyo.assessment.repositories.helper;

public final class ParseQuery {
	private ParseQuery() {}
	
	private static final String[] SPECIAL_CHARS = "\\^$[]()+-*?.|".split("");
	
	public static String parse(String query) {
		String parsedQuery = escapeSpecials(query)
				.trim()
				.replaceAll("\\s+", "|");
		return parsedQuery;
	}
	
	private static String escapeSpecials(String query) {
		String result = query;
		for (String c : SPECIAL_CHARS)
			result = result.replace(c, "\\" + c);
		return result;
	}
}
