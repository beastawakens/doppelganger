package util.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.Customer;

import org.junit.Before;
import org.junit.Test;

import testutils.CustomerHelper;

public class JobMatcherTest {
	
	private JobMatcher matcher;
	private Customer firstCustomer;
	private Customer secondCustomer;

	@Before
	public void setUp() {
		matcher = new JobMatcher();
		firstCustomer = new Customer();
		secondCustomer = new Customer();
	}
	
	@Test
	public void shouldMatchWhereTitleAndOrgIsTheSame() throws Exception {
		String title = CustomerHelper.getSimpleString();
		String org = CustomerHelper.getSimpleString();
		
		firstCustomer.jobTitle = title;
		secondCustomer.jobTitle = title;
		
		firstCustomer.organization = org;
		secondCustomer.organization = org;
		
		assertTrue(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}
	
	@Test
	public void shouldNotMatchWhereTitleIsDifferent() throws Exception {
		String firstTitle = CustomerHelper.getSimpleString();
		String secondTitle = firstTitle + CustomerHelper.getSimpleString();
		String org = CustomerHelper.getSimpleString();
		
		firstCustomer.jobTitle = firstTitle;
		secondCustomer.jobTitle = secondTitle;
		
		firstCustomer.organization = org;
		secondCustomer.organization = org;
		
		assertFalse(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}
	
	@Test
	public void shouldNotMatchWhereOrgIsDifferent() throws Exception {
		String title = CustomerHelper.getSimpleString();
		String firstOrg = CustomerHelper.getSimpleString();
		String secondOrg = firstOrg + CustomerHelper.getSimpleString();
		
		firstCustomer.jobTitle = title;
		secondCustomer.jobTitle = title;
		
		firstCustomer.organization = firstOrg;
		secondCustomer.organization = secondOrg;
		
		assertFalse(matcher.doTheseMatch(firstCustomer, secondCustomer));
	}
}
