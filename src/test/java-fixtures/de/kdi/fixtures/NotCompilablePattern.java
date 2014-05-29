package de.kdi.fixtures;

import de.kdi.validate.ValidateRegex;

public class NotCompilablePattern {

	@ValidateRegex
	private static final String NOT_COMPILABLE_PATTERN = "(abc";
	
}
