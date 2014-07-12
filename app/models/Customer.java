package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Customer extends Model {

	private static final long serialVersionUID = 1448158739602535914L;

	@Id
	public Long id;
	
	@Required
	public String firstName;
	
	@Required
	public String lastName;
	
	public String photoUrl;
	public PhotoType photoType;
	public Gender gender;
	public String age;
	public String organization;
	public String jobTitle;
	public String location;
	
	
	public static Finder<Long, Customer> find = new Finder<Long, Customer>(Long.class, Customer.class);
	
	public static List<Customer> all() {
		return find.all();
	}
	
	public static Long create(Customer customer) {
		customer.save();
		return customer.id;
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
}
