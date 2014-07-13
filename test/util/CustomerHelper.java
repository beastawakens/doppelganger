package util;

import org.apache.commons.lang3.RandomStringUtils;

import scala.util.Random;
import models.Customer;
import models.Gender;
import models.PhotoType;
public class CustomerHelper {
	
	public static Customer newRandomCustomer() {
		Random random = new Random();
		
		Customer customer = new Customer();
		customer.firstName = RandomStringUtils.randomAlphanumeric(10);
		customer.lastName = RandomStringUtils.randomAlphanumeric(10);
		
		customer.age = RandomStringUtils.randomAlphanumeric(3);
		customer.jobTitle = RandomStringUtils.randomAlphanumeric(20);
		customer.organization = RandomStringUtils.randomAlphanumeric(20);
		customer.location = RandomStringUtils.randomAlphanumeric(15);
		customer.photoUrl = RandomStringUtils.randomAlphanumeric(30);
		
		int genderIndex = random.nextInt(Gender.values().length);
		customer.gender = Gender.values()[genderIndex];
		
		int photoTypeIndex = random.nextInt(PhotoType.values().length);
		customer.photoType = PhotoType.values()[photoTypeIndex];
		
		return customer;
	}

}
