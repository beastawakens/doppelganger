package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Customer;

public class DuplicateIdentifier {
	
	List<DuplicateStrategy> strategies = new ArrayList<DuplicateStrategy>();

	public DuplicateIdentifier(DuplicateStrategy... startingStrategies) {
		this.strategies.addAll(Arrays.asList(startingStrategies));
	}
	
	public Set<Set<Customer>> getDuplicates() {
		List<Customer> allCustomers = Customer.all();
		
		Set<Set<Customer>> allMatches = new HashSet<Set<Customer>>();
		
		for (Customer initialCustomer : allCustomers) {
			Set<Customer> matchesWithInitialCustomer = new HashSet<Customer>();
			for (Customer customerToCompareAgainst : allCustomers) {
				if (initialCustomer != customerToCompareAgainst) {
					if (isAMatch(initialCustomer, customerToCompareAgainst)) {
						matchesWithInitialCustomer.add(customerToCompareAgainst);
					}
				}
			}
			if (matchesWithInitialCustomer.size() != 0) {
				matchesWithInitialCustomer.add(initialCustomer);
				allMatches.add(matchesWithInitialCustomer);
			}
		}
		
		return allMatches;
	}

	private boolean isAMatch(Customer initialCustomer, Customer customerToCompareAgainst) {
		boolean matches = false;
		for (DuplicateStrategy strategy : strategies) {
			if (strategy.doTheseMatch(initialCustomer, customerToCompareAgainst)) {
				matches = true;
			}
		}
		return matches;
	}

	public void addStrategy(DuplicateStrategy strategy) {
		strategies.add(strategy);
	}

}
