package nl.sogyo.assessment;

import nl.sogyo.assessment.domainentities.Person;
import nl.sogyo.assessment.repositories.PersonRepository;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoSpringTest implements CommandLineRunner{
	@Autowired
	private PersonRepository person;
	
	public static void main(String[] args) {
		SpringApplication.run(MongoSpringTest.class, args);
	}
	
	@Override
	public void run(String... args) {
		List<Person> persons = person.findByFirstName("Nannie");
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(persons));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
