package controllers;

import java.util.List;

import models.Customer;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Customers extends Controller {
	
	public static Result list() {
		List<Customer> customers = Customer.all();
		return ok(Json.toJson(customers));
	}
	
	public static Result get(Long id) {
		Customer customer = Customer.find.byId(id);
		if (customer == null) {
			return notFound();
		} else {
			return ok(Json.toJson(customer));
		}
	}
	
	public static Result duplicates() {
		return TODO;
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result add() {
		JsonNode incomingJson = request().body().asJson();
		if (incomingJson == null) {
			return badRequest();
		}
		Long newCustomerId = Customer.create(Json.fromJson(incomingJson, Customer.class));
		ObjectNode outgoingJson = Json.newObject();
		outgoingJson.put("id", newCustomerId);
		return created(outgoingJson);
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result update(Long id) {
		Customer originalCustomer = Customer.find.byId(id);
		if (originalCustomer == null) {
			return notFound();
		} else {
			JsonNode incomingJson = request().body().asJson();
			if (incomingJson == null) {
				return badRequest();
			}	
			Customer updatedCustomer = Customer.update(Json.fromJson(incomingJson, Customer.class), originalCustomer);
			return ok(Json.toJson(updatedCustomer));
		}
	}
	
	public static Result delete(Long id) {
		Customer customer = Customer.find.byId(id);
		if (customer == null) {
			return notFound();
		} else {
			Customer.delete(id);
			return noContent();
		}
	}

}
