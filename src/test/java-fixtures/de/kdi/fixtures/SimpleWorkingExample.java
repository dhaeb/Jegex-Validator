package de.kdi.fixtures;

import java.util.regex.Pattern;

import de.kdi.validate.ValidateRegex;

public class SimpleWorkingExample {

	private static final String MATCHABLE = "The quick brown fox jumps over the lazy dog.";
	
	@ValidateRegex
	private static final String REGEX1 = "fox"; // works as expected
	
	@ValidateRegex
	private final String regex2 = "snake"; // works as expected
	
	public static void main(String[] args) {
		Pattern compiledPattern1 = Pattern.compile(REGEX1);
		Pattern compiledPattern2 = Pattern.compile(new SimpleWorkingExample().regex2);
		assert compiledPattern1.matcher(MATCHABLE).find();
		assert !compiledPattern2.matcher(MATCHABLE).find();
	}
	
}
