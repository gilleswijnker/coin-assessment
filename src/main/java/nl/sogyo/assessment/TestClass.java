package nl.sogyo.assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
//import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@JsonRootName(value="Test")
public class TestClass {
	@JsonIgnore
	public String internalId;
	
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getId() {
		return this.id;
	}
	
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	
	public TestClass(String id, String name) {
		this.internalId = id;
		this.name = name;
	}
	
	public static void main(String args[]) {
		TestClass t1 = new TestClass("1", "A");
		TestClass t2 = new TestClass("2", "B");
		
		List<TestClass> l = new ArrayList<>();
		l.add(t1);
		l.add(t2);
		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(;
		try {
			System.out.println(mapper.writeValueAsString(l));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
