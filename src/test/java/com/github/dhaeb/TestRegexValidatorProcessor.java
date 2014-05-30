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

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.processing.Processor;
import javax.tools.Diagnostic.Kind;

import org.junit.Test;

import com.github.dhaeb.RegexValidatorProcessor;
import com.github.dhaeb.fixtures.ExtendedWorkingExample;
import com.github.dhaeb.fixtures.FixturesDontHold;
import com.github.dhaeb.fixtures.NotCompilablePattern;
import com.github.dhaeb.fixtures.NotInitializedPattern;
import com.github.dhaeb.fixtures.NotStringPattern;
import com.github.dhaeb.fixtures.NotVerifyANonFinalField;
import com.github.dhaeb.fixtures.SimpleWorkingExample;
import com.github.dhaeb.fixtures.UsingPatternQuoteFlag;
import com.github.dhaeb.fixtures.WorkingUsage;
import com.qrmedia.commons.test.annotation.processing.AbstractAnnotationProcessorTest;

public class TestRegexValidatorProcessor extends AbstractAnnotationProcessorTest {

	private static final int LINE_NUMBER = 23;

	@Override
	protected Collection<Processor> getProcessors() {
		return Arrays.asList( new Processor[]{new RegexValidatorProcessor()});
	}
	
	@Test
	public void testNotCompilablePatternThrowsException() throws Exception {
		assertCompilationReturned(Kind.ERROR, LINE_NUMBER, compileTestCase(NotCompilablePattern.class));
	}
	
	@Test
	public void testNotInitializedPatternStringWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, LINE_NUMBER, compileTestCase(NotInitializedPattern.class));
	}
	
	@Test
	public void testNotAStringWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, LINE_NUMBER, compileTestCase(NotStringPattern.class));
	}
	
	@Test
	public void testNotFinalWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, LINE_NUMBER, compileTestCase(NotVerifyANonFinalField.class));
	}
	
	@Test
	public void testWorksWithComplexExpression() throws Exception {
		assertCompilationSuccessfulWithoutWarnings(compileTestCase(UsingPatternQuoteFlag.class));
	}
	
	@Test
	public void testWorksNormalUsage() throws Exception {
		assertCompilationSuccessfulWithoutWarnings(compileTestCase(WorkingUsage.class, SimpleWorkingExample.class, ExtendedWorkingExample.class));
	}
	
	@Test
	public void testDoesNotCompileWhenUserFixturesNotMatch() throws Exception {
		assertCompilationReturned(Kind.ERROR, LINE_NUMBER, compileTestCase(FixturesDontHold.class));
	}

}
