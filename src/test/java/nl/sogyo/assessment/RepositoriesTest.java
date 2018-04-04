package nl.sogyo.assessment;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;

import nl.sogyo.assessment.domainentities.Company;
import nl.sogyo.assessment.domainentities.Person;
import nl.sogyo.assessment.repositories.CompanyRepository;
import nl.sogyo.assessment.repositories.PersonRepository;


@RunWith(SpringRunner.class)
@ContextConfiguration
public class RepositoriesTest {
	/*
	 * Setup test environment
	 */
	@ClassRule
	public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();
	
    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("demo-test");

	@Autowired private ApplicationContext applicationContext;
	@Autowired private PersonRepository personRepository;
	@Autowired private CompanyRepository companyRepository;
	
	@Before
	public void setupDB() {
		this.personRepository.deleteAll();
		this.personRepository.save(new PersonTest(1, "First", "Last", "street1", "Male", "123456789"));
		this.personRepository.save(new PersonTest(2, "FirstName", "LastName", "street2", "Female", "987654321"));
		this.personRepository.save(new PersonTest(3, "FirstName", "LastName", "street2", "Female", "987654321"));
		this.companyRepository.save(new CompanyTest(4, "nameC", "street", "582471693"));
		this.companyRepository.save(new CompanyTest(5, "nameC", "street", "741852963"));
		this.companyRepository.save(new CompanyTest(6, "nameC", "street", "741852963"));
	}
	
	/*
	 * Tests
	 */
	@Test
    public void testCountAllPersons() {
         int total = this.personRepository.findAll().size();
         Assert.assertEquals(3, total);
    }
	
	@Test
    public void testCountAllCompanies() {
         int total = this.companyRepository.findAll().size();
         Assert.assertEquals(3, total);
    }
	
	@Test
	public void testFindAndSortPersons() {
		Sort sort = new Sort(Direction.DESC, "id");
		List<Person> persons = personRepository.findAll(sort);
		
		int i = 3;
		for (Person p : persons) {
			Assert.assertEquals(i, p.id);
			i--;
		}
	}
	
	@Test
	public void testFindPagingAndSortingPersons() {
		int page = 2;
		int pageSize = 1;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "id");
		Page<Person> pagePersons = personRepository.findAll(pageable);
		List<Person> persons = pagePersons.getContent();
		Assert.assertEquals(1, persons.get(0).id);
	}
	
	@Test
	public void testFindAndSortCompanies() {
		Sort sort = new Sort(Direction.DESC, "id");
		List<Company> companies= companyRepository.findAll(sort);
		
		int i = 6;
		for (Company c : companies) {
			Assert.assertEquals(i, c.id);
			i--;
		}
	}
	
	@Test
	public void testFindPagingAndSortingCompanies() {
		int page = 2;
		int pageSize = 1;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "id");
		Page<Company> pageCompanies = companyRepository.findAll(pageable);
		List<Company> companies = pageCompanies.getContent();
		Assert.assertEquals(4, companies.get(0).id);
	}
	
//	@Test
//	public void testGetFirstRecord() {
//		int total = this.personRepository.findFromTo(1, 1).size();
//		Assert.assertEquals(1, total);
//	}
	
	/*
	 * inner classes needed to run tests
	 */
	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackageClasses = {PersonRepository.class})
	static class PersonRepoTestConfiguration extends AbstractMongoConfiguration {
	    @Override
	    protected String getDatabaseName() {return "demo-test";}
		
	    @Bean
	    @Override
		public MongoClient mongoClient() {return new Fongo("mongo-test").getMongo();}
		
	    @Override
	    protected String getMappingBasePackage() {return "nl.sogyo.assessment";}
	}
	
	private class PersonTest extends Person {
		public PersonTest (int id, String firstName, String lastName, String address, String gender, String phoneNumber) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.gender = gender;
			this.phoneNumber = phoneNumber;
		}
	}
	
	private class CompanyTest extends Company {
		public CompanyTest (int id, String companyName, String address, String phoneNumber) {
			this.id = id;
			this.companyName = companyName;
			this.address = address;
			this.phoneNumber = phoneNumber;
		}
	}
}
