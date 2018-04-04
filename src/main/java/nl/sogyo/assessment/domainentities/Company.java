package nl.sogyo.assessment.domainentities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="assessment")
public class Company {
	@Id
	public String _id;
	
	public int id;
	public String companyName;
	public String address;
	public String phoneNumber;
}
