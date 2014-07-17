package util.strategies;

import models.Customer;
import util.DuplicateStrategy;

public class CaseInsensitiveLastNameMatcher implements DuplicateStrategy {

	@Override
	public boolean doTheseMatch(Customer firstCustomer, Customer secondCustomer) {
		return firstCustomer.lastName.equalsIgnoreCase(secondCustomer.lastName);
	}

}
