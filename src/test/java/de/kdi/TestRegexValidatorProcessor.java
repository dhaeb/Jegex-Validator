package de.kdi;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.processing.Processor;
import javax.tools.Diagnostic.Kind;

import org.junit.Test;

import com.qrmedia.commons.test.annotation.processing.AbstractAnnotationProcessorTest;

import de.kdi.fixtures.FixturesDontHold;
import de.kdi.fixtures.NotCompilablePattern;
import de.kdi.fixtures.NotInitializedPattern;
import de.kdi.fixtures.NotStringPattern;
import de.kdi.fixtures.NotVerifyANonFinalField;
import de.kdi.fixtures.UsingPatternQuoteFlag;
import de.kdi.fixtures.WorkingUsage;

public class TestRegexValidatorProcessor extends AbstractAnnotationProcessorTest {

	@Override
	protected Collection<Processor> getProcessors() {
		return Arrays.asList( new Processor[]{new RegexValidatorProcessor()});
	}
	
	@Test
	public void testNotCompilablePatternThrowsException() throws Exception {
		assertCompilationReturned(Kind.ERROR, 8, compileTestCase(NotCompilablePattern.class));
	}
	
	@Test
	public void testNotInitializedPatternStringWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, 8, compileTestCase(NotInitializedPattern.class));
	}
	
	@Test
	public void testNotAStringWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, 8, compileTestCase(NotStringPattern.class));
	}
	
	@Test
	public void testNotFinalWarning() throws Exception {
		assertCompilationReturned(Kind.WARNING, 8, compileTestCase(NotVerifyANonFinalField.class));
	}
	
	@Test
	public void testWorksWithComplexExpression() throws Exception {
		assertCompilationSuccessfulWithoutWarnings(compileTestCase(UsingPatternQuoteFlag.class));
	}
	
	@Test
	public void testWorksWithLocalVariable() throws Exception {
		assertCompilationSuccessfulWithoutWarnings(compileTestCase(WorkingUsage.class));
	}
	
	@Test
	public void testDoesNotCompileWhenUserFixturesNotMatch() throws Exception {
		assertCompilationReturned(Kind.ERROR, 8, compileTestCase(FixturesDontHold.class));
	}

}
