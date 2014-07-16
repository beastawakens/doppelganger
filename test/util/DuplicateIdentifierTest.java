package util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import models.Customer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import testutils.Base;
import testutils.CustomerHelper;

public class DuplicateIdentifierTest extends Base {
	
	@Mock
	DuplicateStrategy strategy;
	private Customer customer1;
	private Customer customer2;
	
	@Before
	public void setUp() {
		customer1 = CustomerHelper.newBasicCustomer();
		customer1.save();
		customer2 = CustomerHelper.newBasicCustomer();
		customer2.save();
	}
	
	@Test
	public void shouldDelegateToStrategy() throws Exception {
		DuplicateIdentifier identifier = new DuplicateIdentifier();
		identifier.addStrategy(strategy);
		
		when(strategy.doTheseMatch(customer1, customer2)).thenReturn(true);
		
		Set<Set<Customer>> duplicates = identifier.getDuplicates();
		
		verify(strategy).doTheseMatch(customer1, customer2);

		assertThat(duplicates.size(), is(1));
		Set<Customer> nextBunchOfDuplicates = duplicates.iterator().next();
		assertThat(nextBunchOfDuplicates.size(), is(2));
		assertTrue(nextBunchOfDuplicates.contains(customer1));
		assertTrue(nextBunchOfDuplicates.contains(customer1));
	}
}
