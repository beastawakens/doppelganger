package controllers;

import models.Customer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Doppelganger extends Controller {

	private static final String TITLE = "Dopplegänger";
	static Form<Customer> customerForm = Form.form(Customer.class);

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

}
