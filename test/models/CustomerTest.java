package models;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import testutils.Base;
import testutils.CustomerHelper;

public class CustomerTest extends Base {
	
	@Test
	public void shouldReturnNewIdUponCreation() throws Exception {
		Customer newCustomer = CustomerHelper.newRandomCustomer();
		
		assertThat(newCustomer.id, is(nullValue()));
		Long returnedId = Customer.create(newCustomer);
		
		assertThat(returnedId, is(notNullValue()));
		assertThat(returnedId, is(newCustomer.id));
	}
	
	@Test
	public void shouldUpdateTheOriginalCustomer() throws Exception {
		Customer originalCustomer = CustomerHelper.newRandomCustomer();
		
		Customer updatedCustomer = CustomerHelper.newRandomCustomer();
		
		assertFalse(originalCustomer.firstName.equals(updatedCustomer.firstName));
		assertFalse(originalCustomer.lastName.equals(updatedCustomer.lastName));
		assertFalse(originalCustomer.age.equals(updatedCustomer.age));
		
		Customer.update(updatedCustomer, originalCustomer);
		
		assertTrue(originalCustomer.firstName.equals(updatedCustomer.firstName));
		assertTrue(originalCustomer.lastName.equals(updatedCustomer.lastName));
		assertTrue(originalCustomer.age.equals(updatedCustomer.age));
	}

}
