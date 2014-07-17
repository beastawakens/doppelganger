package controllers;

import java.util.Set;

import models.Customer;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.DuplicateIdentifier;
import util.strategies.CaseInsensitiveLastNameMatcher;
import util.strategies.JobMatcher;

public class Doppelganger extends Controller {
	
	private static final String TITLE = "Doppelgänger";
	static Form<Customer> customerForm = Form.form(Customer.class);
	
	private static DuplicateIdentifier duplicateIdentifier;


	public static Result index(Long id) {
		return ok(views.html.index.render(TITLE, id, customerForm));
	}

	public static Result newCustomer() {
		Form<Customer> filledForm = customerForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(TITLE, 29418l, customerForm));
		} else {
			Long id = Customer.create(filledForm.get());
			return redirect(routes.Doppelganger.index(id));
		}
	}
	
	public static Result duplicates() {
		Set<Set<Customer>> duplicates = getDuplicateIdentifier().getDuplicates();
		
		return ok(Json.toJson(duplicates));
	}

	public static DuplicateIdentifier getDuplicateIdentifier() {
		if (duplicateIdentifier == null) {
			duplicateIdentifier = new DuplicateIdentifier(new CaseInsensitiveLastNameMatcher(), new JobMatcher());
		}
		return duplicateIdentifier;
	}

	public static void setDuplicateIdentifier(DuplicateIdentifier duplicateIdentifier) {
		Doppelganger.duplicateIdentifier = duplicateIdentifier;
	}

}
