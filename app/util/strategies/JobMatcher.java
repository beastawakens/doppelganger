package util.strategies;

import models.Customer;
import util.DuplicateStrategy;

public class JobMatcher implements DuplicateStrategy {

	@Override
	public boolean doTheseMatch(Customer firstCustomer, Customer secondCustomer) {
		return firstCustomer.jobTitle.equals(secondCustomer.jobTitle) 
				&& firstCustomer.organization.equals(secondCustomer.organization);
	}

}
