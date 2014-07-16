package util;

import models.Customer;

public interface DuplicateStrategy {

	boolean doTheseMatch(Customer firstCustomer, Customer secondCustomer);

}
