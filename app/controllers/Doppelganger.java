package controllers;

import models.Customer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Doppelganger extends Controller {
	
	static Form<Customer> customerForm = Form.form(Customer.class);

    public static Result index() {
        return ok(views.html.index.render("Dopplegänger", customerForm));
    }
    
    public static Result newCustomer() {
    	return TODO;
    }

}
