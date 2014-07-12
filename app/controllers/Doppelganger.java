package controllers;

import models.Customer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Doppelganger extends Controller {
	
	Form<Customer> customerForm = Form.form(Customer.class);

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
