package nl.sogyo.assessment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Document(collection="assessment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataEntity {
	@Id
	private String mongoId;
	
	@JsonProperty private int id;
	@JsonProperty private String firstName;
	@JsonProperty private String lastName;
	@JsonProperty private String companyName;
	@JsonProperty private String address;
	@JsonProperty private String gender;	
	@JsonProperty private String phoneNumber;
	
	@JsonProperty private String personOrCompany;
	
	private final static String COMPANY = "company";
	private final static String PERSON = "person"; 
	private final static ObjectMapper MAPPER = new ObjectMapper();
	
	// required for Spring to work properly
	public DataEntity() {};
	
	// company constructor
	public DataEntity(int id, String companyName, String address, String phoneNumber) {
		this.id = id;
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	// person constructor
	public DataEntity(int id, String firstName, String lastName, String address, String gender, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}
	
	public String toJson() {
		this.determinePersonOrCompany();
		try {
			return MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	private void determinePersonOrCompany() {
		this.personOrCompany = (this.companyName != null ? DataEntity.COMPANY : DataEntity.PERSON);
	}
}
