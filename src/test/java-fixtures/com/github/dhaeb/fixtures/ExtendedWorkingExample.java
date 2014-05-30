package com.github.dhaeb.fixtures;

import java.util.regex.Pattern;

import com.github.dhaeb.validate.ValidateRegex;

public class ExtendedWorkingExample {

	private static final String MATCHABLE = "The quick brown fox jumps over the lazy dog.";
	
	@ValidateRegex(matches={"What does the fox say?", MATCHABLE})
	private static final String REGEX1 = ".*fox.*"; // compiles when all strings of the matches array matches() the patter
	
	@ValidateRegex(matches="The snake")
	private final String regex2 = ".*snake$"; 
	
	public static void main(String[] args) {
		Pattern compiledPattern1 = Pattern.compile(REGEX1);
		Pattern compiledPattern2 = Pattern.compile(new ExtendedWorkingExample().regex2);
		assert compiledPattern1.matcher(MATCHABLE).matches();
		assert !compiledPattern2.matcher(MATCHABLE).matches();
	}
	
}
