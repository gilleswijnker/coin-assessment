package nl.sogyo.assessment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestClass {
	public String a = "testa";
	public String b = "testb";
	
	public static void main(String args[]) {
		TestClass tc = new TestClass();
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(tc));
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
	}
}