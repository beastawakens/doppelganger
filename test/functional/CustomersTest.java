package functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import java.util.Random;

import models.Customer;

import org.junit.Test;

import play.libs.Json;
import play.mvc.Result;
import util.Base;
import util.CustomerHelper;

public class CustomersTest extends Base {

	@Test
	public void shouldReturnAllCustomers() throws Exception {
		Result result = callAction(controllers.routes.ref.Customers.list());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		assertThat(contentAsString(result), is("[]"));
		
		Customer firstCustomer = CustomerHelper.newRandomCustomer();
		firstCustomer.save();
		Customer secondCustomer = CustomerHelper.newRandomCustomer();
		secondCustomer.save();

		result = callAction(controllers.routes.ref.Customers.list());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		String body = contentAsString(result);
		
		assertTrue(body.contains(firstCustomer.firstName));
		assertTrue(body.contains(secondCustomer.firstName));
		assertTrue(body.contains(firstCustomer.lastName));
		assertTrue(body.contains(secondCustomer.lastName));
	}
	
	@Test
	public void shouldNotBeAbleToFindAnInvalidCustomer() throws Exception {
		Result result = callAction(controllers.routes.ref.Customers.get(1l));
		assertThat(status(result), is(NOT_FOUND));

		result = callAction(controllers.routes.ref.Customers.get(new Random().nextLong()));
		assertThat(status(result), is(NOT_FOUND));
	}
	
	@Test
	public void shouldReturnSpecificCustomer() throws Exception {
		Customer customer = CustomerHelper.newRandomCustomer();
		customer.save();

		Result result = callAction(controllers.routes.ref.Customers.get(customer.id));
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		
		String body = contentAsString(result);
		Customer rebuiltCustomer = Json.fromJson(Json.parse(body), Customer.class);
		
		assertThat(rebuiltCustomer.id, is(customer.id));
		assertThat(rebuiltCustomer.firstName, is(customer.firstName));
		assertThat(rebuiltCustomer.lastName, is(customer.lastName));
		assertThat(rebuiltCustomer, is(customer));
	}

}
