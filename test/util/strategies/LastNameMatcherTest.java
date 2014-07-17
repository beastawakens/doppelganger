package util.strategies;

import static org.junit.Assert.*;
import models.Customer;

import org.junit.Test;

import testutils.CustomerHelper;

public class LastNameMatcherTest {
	
	@Test
	public void shouldMatchWhereLastNameIsIdentical() throws Exception {
		LastNameMatcher matcher = new LastNameMatcher();
		
		Customer firstCustomer = CustomerHelper.newBasicCustomer();
		Customer secondCustomer = CustomerHelper.newBasicCustomer();
		secondCustomer.lastName = firstCustomer.lastName;
		boolean doTheseMatch = matcher.doTheseMatch(firstCustomer, secondCustomer);
		
		assertTrue(doTheseMatch);
	}
	
	@Test
	public void shouldMatchWhereLastNameIsTheSameButNotInCase() throws Exception {
		LastNameMatcher matcher = new LastNameMatcher();
		
		Customer upperCaseCustomer = CustomerHelper.newBasicCustomer();
		Customer lowerCaseCustomer = CustomerHelper.newBasicCustomer();
		lowerCaseCustomer.lastName = upperCaseCustomer.lastName;
		lowerCaseCustomer.lastName = lowerCaseCustomer.lastName.toLowerCase();
		upperCaseCustomer.lastName = upperCaseCustomer.lastName.toUpperCase();
		boolean doTheseMatch = matcher.doTheseMatch(upperCaseCustomer, lowerCaseCustomer);
		
		assertTrue(doTheseMatch);
	}
	
	@Test
	public void shouldNotMatchWhereLastNameIsNotIdentical() throws Exception {
		LastNameMatcher matcher = new LastNameMatcher();
		
		Customer firstCustomer = CustomerHelper.newBasicCustomer();
		Customer secondCustomer = CustomerHelper.newBasicCustomer();
		secondCustomer.lastName = firstCustomer.lastName + firstCustomer.firstName;
		boolean doTheseMatch = matcher.doTheseMatch(firstCustomer, secondCustomer);
		
		assertFalse(doTheseMatch);
	}

}
