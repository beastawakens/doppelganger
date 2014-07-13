package models;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
	unknown, male, female;
	
	public static List<String> options() {
		ArrayList<String> options = new ArrayList<String>();
		for (Gender gender : Gender.values()) {
			options.add(gender.name());
		}
		return options;
	}
}
