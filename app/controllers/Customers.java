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
		Customer customer = Customer.find.ref(id);
		if (customer == null) {
			return notFound();
		} else {
			return ok(Json.toJson(customer));
		}
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result add() {
		JsonNode incomingJson = request().body().asJson();
		Long newCustomerId = Customer.create(Json.fromJson(incomingJson, Customer.class));
		ObjectNode outgoingJson = Json.newObject();
		outgoingJson.put("id", newCustomerId);
		return created(outgoingJson);
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result update(Long id) {
		return TODO;
	}
	
	public static Result delete(Long id) {
		Customer customer = Customer.find.ref(id);
		if (customer == null) {
			return notFound();
		} else {
			Customer.delete(id);
			return noContent();
		}
	}

}
