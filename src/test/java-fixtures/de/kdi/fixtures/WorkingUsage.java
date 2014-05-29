package de.kdi.fixtures;

import java.util.regex.Pattern;

import de.kdi.validate.ValidateRegex;

public class WorkingUsage {

	@ValidateRegex
	private static final String CONSTANT = "[^123]*"; // works as expected
	
	@ValidateRegex
	private final String field = "pattern"; // works only with a final field!
	
	public boolean matches(String input){
		@ValidateRegex final String localVariable = "compilable"; // this is not processed using javac 1.7.55!!!
		Pattern compile = Pattern.compile(localVariable);
		return compile.matcher(input).matches();
	}
	
}
