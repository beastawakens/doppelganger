package models;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

public class CustomerTest {
	
	@Test
	public void shouldReturnNewIdUponCreation() throws Exception {
		
		Customer newCustomer = new Customer();
		Long returnedId = Customer.create(newCustomer);
		
		assertThat(returnedId, is(0l));
	}

}
