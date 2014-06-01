/* 
 * Copyright 2014 Dan Häberlein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.github.dhaeb;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

import com.github.dhaeb.exceptions.NotMatchableException;
import com.github.dhaeb.validate.ValidateRegex;

@SupportedAnnotationTypes("com.github.dhaeb.validate.ValidateRegex")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RegexValidatorProcessor extends AbstractProcessor {

	private static final String CAN_T_CHECK_REGEX = "Can't check regex: ";

	@Override
	public boolean process(Set<? extends TypeElement> annotatedElement, RoundEnvironment env) {
		if (env.processingOver()) {
			getMessager().printMessage(Kind.NOTE, String.format("Used Jegex-Validator Version %s", "0.0.1"));
		} else {
			Set<? extends Element> annotatedElements = env.getElementsAnnotatedWith(ValidateRegex.class);
			for (Element e : annotatedElements) {
				checkIfPatternCompilble((VariableElement) e);
			}
		}
		return true;
	}

	private void checkIfPatternCompilble(VariableElement e) {
		// System.err.println(e.getSimpleName() + " = " + e.getConstantValue());
		checkFieldIsFinal(e, e.getAnnotation(ValidateRegex.class));
	}

	private void checkFieldIsFinal(VariableElement e, ValidateRegex annotation) {
		if (fieldIsFinal(e)) {
			checkIsString(e, annotation);
		} else {
			getMessager().printMessage(Kind.WARNING, String.format(CAN_T_CHECK_REGEX + "Variable %s is not final!", e.getSimpleName()), e);
		}
	}

	private boolean fieldIsFinal(VariableElement e) {
		return e.getModifiers().contains(Modifier.FINAL);
	}

	private void checkIsString(VariableElement e, ValidateRegex annotation) {
		TypeMirror variableType = e.asType();
		if (isSameType(getTypeElementFromClass(String.class).asType(), variableType)) {
			String pattern = (String) e.getConstantValue();
			checkIsNullOrNoFunctionCall(e, annotation, pattern);
		} else {
			getMessager().printMessage(Kind.WARNING,
					String.format(CAN_T_CHECK_REGEX + "Variable %s not a String! Instead found type %s", e.getSimpleName(), variableType), e);
		}
	}

	private void checkIsNullOrNoFunctionCall(VariableElement e, ValidateRegex annotation, String pattern) {
		if (pattern == null) {
			getMessager().printMessage(Kind.ERROR,
					String.format(CAN_T_CHECK_REGEX + "Variable %s is null! (Or is it maybe not a constant string value?)", e.getSimpleName()), e);
		} else {
			processPattern(e, annotation, pattern);
		}
	}

	private void processPattern(VariableElement e, ValidateRegex annotation, String pattern) {
		pattern = quoteIfRequested(annotation, pattern);
		try {
			verifyPattern(annotation, pattern);
		} catch (PatternSyntaxException e1) {
			getMessager().printMessage(Kind.ERROR, String.format("Regular expression is not well formed: %s", e1.getMessage()), e);
		} catch (NotMatchableException e1) {
			getMessager().printMessage(Kind.ERROR, e1.getMessage(), e);
		}
	}

	private String quoteIfRequested(ValidateRegex annotation, String pattern) {
		if (annotation.patternQuote()) {
			pattern = Pattern.quote(pattern);
		}
		return pattern;
	}

	private void verifyPattern(ValidateRegex annotation, String pattern) throws PatternSyntaxException, NotMatchableException {
		Pattern compiledPattern = Pattern.compile(pattern); // throws PatternSyntaxException when pattern not well-formed
		for (String souldBeMatchable : annotation.matches()) {
			boolean matchesUserdefinedInput = compiledPattern.matcher(souldBeMatchable).matches();
			if (!matchesUserdefinedInput) {
				throw new NotMatchableException(String.format("Matched not user input: \"%s\"", souldBeMatchable));
			}
		}
	}

	private Messager getMessager() {
		return processingEnv.getMessager();
	}

	private boolean isSameType(TypeMirror stringTypeMirror, TypeMirror variableType) {
		return processingEnv.getTypeUtils().isSameType(variableType, stringTypeMirror);
	}

	private TypeElement getTypeElementFromClass(Class<?> type) {
		return processingEnv.getElementUtils().getTypeElement(type.getName());
	}

}
