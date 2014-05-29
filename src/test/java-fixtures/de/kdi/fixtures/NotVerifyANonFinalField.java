package de.kdi.fixtures;

import de.kdi.validate.ValidateRegex;

public class NotVerifyANonFinalField {

	@ValidateRegex
	private static String NOT_FINAL = ".*";
}
