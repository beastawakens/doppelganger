package functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import java.util.HashSet;
import java.util.Set;

import models.Customer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import play.mvc.Result;
import util.Base;
import util.CustomerHelper;
import util.DuplicateIdentifier;
import controllers.Doppelganger;

public class DoppelgangerTest extends Base {
	
	@Mock
	public static DuplicateIdentifier duplicateIdentifier;
	
	@Before
	public void setUp() {
		Doppelganger.setDuplicateIdentifier(duplicateIdentifier);
	}
	
	@Test
	public void shouldRetrieveEmptyDuplicates() throws Exception {
		
		Set<Set<Customer>> allDuplicates = new HashSet<Set<Customer>>();
		when(duplicateIdentifier.getDuplicates()).thenReturn(allDuplicates);
		
		Result result = callAction(controllers.routes.ref.Doppelganger.duplicates());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		assertThat(contentAsString(result), is("[]"));
	}
	
	@Test
	public void shouldRetrieveValidDuplicates() throws Exception {

		Customer customerA1 = CustomerHelper.newBasicCustomer();
		Customer customerA2 = CustomerHelper.newBasicCustomer();
		Customer customerB1 = CustomerHelper.newBasicCustomer();
		Customer customerB2 = CustomerHelper.newBasicCustomer();
		
		customerA1.save();
		customerA2.save();
		customerB1.save();
		customerB2.save();
		
		Set<Customer> firstSetOfDuplicates = new HashSet<Customer>();
		Set<Customer> secondSetOfDuplicates = new HashSet<Customer>();
		
		firstSetOfDuplicates.add(customerA1);
		firstSetOfDuplicates.add(customerA2);
		
		secondSetOfDuplicates.add(customerB1);
		secondSetOfDuplicates.add(customerB2);
		
		Set<Set<Customer>> allDuplicates = new HashSet<Set<Customer>>();
		allDuplicates.add(firstSetOfDuplicates);
		allDuplicates.add(secondSetOfDuplicates);
		
		when(duplicateIdentifier.getDuplicates()).thenReturn(allDuplicates);
		
		Result result = callAction(controllers.routes.ref.Doppelganger.duplicates());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		assertThat(contentAsString(result), is("[["
				+ "{\"id\":" + customerA1.id + ",\"firstName\":\"" + customerA1.firstName + "\",\"lastName\":\"" + customerA1.lastName + "\"},"
				+ "{\"id\":" + customerA2.id + ",\"firstName\":\"" + customerA2.firstName + "\",\"lastName\":\"" + customerA2.lastName + "\"}" 
					+ "],["
				+ "{\"id\":" + customerB1.id + ",\"firstName\":\"" + customerB1.firstName + "\",\"lastName\":\"" + customerB1.lastName + "\"},"
				+ "{\"id\":" + customerB2.id + ",\"firstName\":\"" + customerB2.firstName + "\",\"lastName\":\"" + customerB2.lastName + "\"}"
					+ "]]"));
	}
}
