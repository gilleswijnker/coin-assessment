package nl.sogyo.assessment;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.assessment.repositories.helper.ParseQuery;

public class ParseQueryTest {
	@Test
	public void isAlphaNumericStringParsedCorrectly() {
		final String testString = "   this   is a  t3st str1ng  ";
		final String expectedString = "this|is|a|t3st|str1ng";
		final String result = ParseQuery.parse(testString);
		Assert.assertEquals(expectedString, result);
	}
	
	@Test
	public void areSpecialCharactersEscapedProperly() {
		final String testString = "^$[]()+-*?.\\|";
		final String expectedString = "\\^\\$\\[\\]\\(\\)\\+\\-\\*\\?\\.\\\\\\|";
		final String result = ParseQuery.parse(testString);
		Assert.assertEquals(expectedString, result);
	}
	
	@Test
	public void isMixedStringParsedCorrectly() {
		final String testString = "Th1s |s a t.st s?tr1ing";
		final String expectedString = "Th1s|\\|s|a|t\\.st|s\\?tr1ing";
		final String result = ParseQuery.parse(testString);
		Assert.assertEquals(expectedString, result);
	}
	
}
