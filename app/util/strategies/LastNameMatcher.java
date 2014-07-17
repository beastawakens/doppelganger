package util.strategies;

import models.Customer;
import util.DuplicateStrategy;

public class LastNameMatcher implements DuplicateStrategy {

	@Override
	public boolean doTheseMatch(Customer firstCustomer, Customer secondCustomer) {
		return firstCustomer.lastName.equalsIgnoreCase(secondCustomer.lastName);
	}

}
