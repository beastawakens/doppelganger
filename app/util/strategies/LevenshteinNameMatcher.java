package util.strategies;

import org.apache.commons.lang3.StringUtils;

import models.Customer;
import util.DuplicateStrategy;

public class LevenshteinNameMatcher implements DuplicateStrategy {

	private int distance;
	
	public LevenshteinNameMatcher(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean doTheseMatch(Customer firstCustomer, Customer secondCustomer) {
		return StringUtils.getLevenshteinDistance(firstCustomer.firstName, secondCustomer.firstName, distance) != -1
				&& StringUtils.getLevenshteinDistance(firstCustomer.lastName, secondCustomer.lastName, distance) != -1;
	}

}
