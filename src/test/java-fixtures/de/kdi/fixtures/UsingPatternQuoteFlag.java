package de.kdi.fixtures;

import de.kdi.validate.ValidateRegex;

public class UsingPatternQuoteFlag {

	@ValidateRegex(patternQuote = true)
	public static final String QUOTED_PATTERN = "(I will be a valid pattern";
}
