package nl.sogyo.assessment.domainentities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="assessment")
public class Person {
	@Id
	public String _id;
	
	public int id;
	public String firstName;
	public String lastName;
	public String address;
	public String gender;
	public String phoneNumber;
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
}
