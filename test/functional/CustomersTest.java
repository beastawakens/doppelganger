package functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.NO_CONTENT;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import java.util.List;
import java.util.Random;

import models.Customer;

import org.junit.Test;

import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;
import testutils.Base;
import testutils.CustomerHelper;

import com.fasterxml.jackson.databind.JsonNode;

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
	}
	
	@Test
	public void shouldNotAddMalformedCustomer() throws Exception {
		FakeRequest fakeRequest = new FakeRequest().withTextBody("not json at all");
		Result result = callAction(controllers.routes.ref.Customers.add(), fakeRequest);
		
		assertThat(status(result), is(BAD_REQUEST));
	}
	
	@Test
	public void shouldAddCustomer() throws Exception {
		Customer customer = CustomerHelper.newRandomCustomer();
		JsonNode customerAsJson = Json.toJson(customer);

		List<Customer> allCustomersBefore = Customer.find.all();
		
		assertThat(allCustomersBefore.size(), is(0));
		
		FakeRequest fakeRequest = new FakeRequest().withJsonBody(customerAsJson);
		Result result = callAction(controllers.routes.ref.Customers.add(), fakeRequest);
		assertThat(status(result), is(CREATED));
		assertThat(contentType(result), is("application/json"));
		
		String body = contentAsString(result);
		
		List<Customer> allCustomersAfter = Customer.find.all();
		
		assertThat(allCustomersAfter.size(), is(1));
		Customer storedCustomer = allCustomersAfter.get(0);
		
		assertThat(body, is("{\"id\":" + storedCustomer.id + "}"));
	}
	
	@Test
	public void shouldUpdateAValidCustomer() throws Exception {
		Customer originalCustomer = CustomerHelper.newRandomCustomer();
		originalCustomer.save();
		
		Customer updatedCustomer = CustomerHelper.newRandomCustomer();
		JsonNode updatedCustomerAsJson = Json.toJson(updatedCustomer);
		FakeRequest fakeRequest = new FakeRequest().withJsonBody(updatedCustomerAsJson);
		Result result = callAction(controllers.routes.ref.Customers.update(originalCustomer.id), fakeRequest);
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		
		String body = contentAsString(result);
		Customer rebuiltCustomer = Json.fromJson(Json.parse(body), Customer.class);
		
		assertThat(rebuiltCustomer.id, is(originalCustomer.id));
		assertThat(rebuiltCustomer.firstName, is(updatedCustomer.firstName));
		assertThat(rebuiltCustomer.lastName, is(updatedCustomer.lastName));
		
		Customer retrievedCustomer = Customer.find.byId(originalCustomer.id);
		
		assertThat(retrievedCustomer.firstName, is(updatedCustomer.firstName));
		assertThat(retrievedCustomer.lastName, is(updatedCustomer.lastName));
	}
	
	@Test
	public void shouldNotBeAbleUpdateAnInvalidCustomer() throws Exception {
		Result result = callAction(controllers.routes.ref.Customers.update(1l));
		assertThat(status(result), is(NOT_FOUND));

		result = callAction(controllers.routes.ref.Customers.update(new Random().nextLong()));
		assertThat(status(result), is(NOT_FOUND));
	}
	
	@Test
	public void shouldNotBeAbleToUpdateAMalformedCustomer() throws Exception {
		Customer originalCustomer = CustomerHelper.newRandomCustomer();
		originalCustomer.save();
		
		FakeRequest fakeRequest = new FakeRequest().withTextBody("not json at all");
		Result result = callAction(controllers.routes.ref.Customers.update(originalCustomer.id), fakeRequest);
		
		assertThat(status(result), is(BAD_REQUEST));
	}
	
	@Test
	public void shouldDeleteACustomer() throws Exception {
		Customer originalCustomer = CustomerHelper.newRandomCustomer();
		originalCustomer.save();
		
		List<Customer> allCustomersBefore = Customer.find.all();
		
		assertThat(allCustomersBefore.size(), is(1));
		
		Result result = callAction(controllers.routes.ref.Customers.delete(originalCustomer.id));
		assertThat(status(result), is(NO_CONTENT));
		
		List<Customer> allCustomersAfter = Customer.find.all();
		
		assertThat(allCustomersAfter.size(), is(0));
	}
	
	@Test
	public void shouldNotDeleteAnInvalidCustomer() throws Exception {
		Result result = callAction(controllers.routes.ref.Customers.delete(1l));
		assertThat(status(result), is(NOT_FOUND));

		result = callAction(controllers.routes.ref.Customers.delete(new Random().nextLong()));
		assertThat(status(result), is(NOT_FOUND));
	}

}
