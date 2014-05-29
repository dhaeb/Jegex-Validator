package de.kdi.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

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
	
	/**
	 * These parameters can be used to ensure that the pattern matches given input strings.
	 * Results in a compile error when one of the input strings is not matched by the annotated regular expression string. 
	 * 
	 * @return samples which must be matched at compile time
	 */
	String[] matches() default {};
	
	/**
	 * Sometimes it is convenient to use {@link Pattern#quote(String)} for regular expressions. 
	 * Using this parameter you can request to apply this function to your annotated String before 
	 * letting the annotation processor verifying the pattern.<br/>
	 * The default is false.
	 * 
	 * @return A boolean to decide when using {@link Pattern#quote(String)}
	 */
	boolean patternQuote() default false;
}

