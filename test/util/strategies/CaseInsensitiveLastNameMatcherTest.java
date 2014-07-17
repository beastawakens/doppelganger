package util.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.Customer;

import org.junit.Before;
import org.junit.Test;

import testutils.CustomerHelper;

public class CaseInsensitiveLastNameMatcherTest {
	
	private CaseInsensitiveLastNameMatcher matcher;
	private Customer firstCustomer;
	private Customer secondCustomer;

	@Before
	public void setUp() {
		matcher = new CaseInsensitiveLastNameMatcher();
		firstCustomer = CustomerHelper.newBasicCustomer();
		secondCustomer = CustomerHelper.newBasicCustomer();
	}
	
	@Test
	public void shouldMatchWhereLastNameIsIdentical() throws Exception {
		secondCustomer.lastName = firstCustomer.lastName;

		assertTrue(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}
	
	@Test
	public void shouldMatchWhereLastNameIsTheSameButNotInCase() throws Exception {
		secondCustomer.lastName = firstCustomer.lastName;
		secondCustomer.lastName = secondCustomer.lastName.toLowerCase();
		firstCustomer.lastName = firstCustomer.lastName.toUpperCase();
		
		assertTrue(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}
	
	@Test
	public void shouldNotMatchWhereLastNameIsNotIdentical() throws Exception {
		secondCustomer.lastName = firstCustomer.lastName + firstCustomer.firstName;
		
		assertFalse(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}

}
