package de.kdi.fixtures;

import de.kdi.validate.ValidateRegex;

public class FixturesDontHold {

	@ValidateRegex(matches= {"123", "abc"}) // user defines example matches  
	private final String regex = "\\d*";
}
