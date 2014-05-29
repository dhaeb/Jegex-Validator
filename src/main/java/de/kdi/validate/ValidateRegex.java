package de.kdi.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.kdi.RegexValidatorProcessor;

/**
 * 
 * Annotation to verify if an instance of {@link String} can be interpreted as an well-formed regular expression.
 * Therefore, you can only annotate instances of {@link String}.
 * These strings are compile time checked by @link {@link RegexValidatorProcessor}. 
 * You can only use final variables.
 * Local variables don't work using the sun compiler!!! 
 * 
 * @author Dan Häberlein
 *
 */
@Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ValidateRegex {
	
	String[] matches() default {};
	boolean patternQuote() default false;
}

