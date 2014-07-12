package models;

import java.util.ArrayList;
import java.util.List;

public enum PhotoType {
	unknown, gravatar, twitter,	facebook, googleprofile, googleplus, linkedin;
	
	public static List<String> options() {
		ArrayList<String> options = new ArrayList<String>();
		for (PhotoType photoType: PhotoType.values()) {
			options.add(photoType.name());
		}
		return options;
	}
}
