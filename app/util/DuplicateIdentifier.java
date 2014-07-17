package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Customer;

public class DuplicateIdentifier {
	
	private List<DuplicateStrategy> strategies = new ArrayList<DuplicateStrategy>();
	private List<Customer> allCustomers;

	public DuplicateIdentifier(DuplicateStrategy... startingStrategies) {
		this.strategies.addAll(Arrays.asList(startingStrategies));
	}
	
	public Set<Set<Customer>> getDuplicates() {
		allCustomers = Customer.all();
		
		Set<Set<Customer>> allMatches = new HashSet<Set<Customer>>();
		
		for (Customer customer : allCustomers) {
			Set<Customer> matchesWithInitialCustomer = compareAgainst(customer);
			
			if (matchesWithInitialCustomer.size() != 0) {
				matchesWithInitialCustomer.add(customer);
				allMatches.add(matchesWithInitialCustomer);
			}
		}
		
		return allMatches;
	}

	private Set<Customer> compareAgainst(Customer initialCustomer) {
		Set<Customer> matchesWithInitialCustomer = new HashSet<Customer>();
		for (Customer customerToCompareAgainst : allCustomers) {
			if ((initialCustomer != customerToCompareAgainst) && (isAMatch(initialCustomer, customerToCompareAgainst))) {
				matchesWithInitialCustomer.add(customerToCompareAgainst);
			}
		}
		return matchesWithInitialCustomer;
	}

	private boolean isAMatch(Customer initialCustomer, Customer customerToCompareAgainst) {
		for (DuplicateStrategy strategy : strategies) {
			if (strategy.doTheseMatch(initialCustomer, customerToCompareAgainst)) {
				return true;
			}
		}
		return false;
	}

	public void addStrategy(DuplicateStrategy strategy) {
		strategies.add(strategy);
	}

}
