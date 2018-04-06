package nl.sogyo.assessment;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;

import nl.sogyo.assessment.domain.DatabaseEntity;
import nl.sogyo.assessment.repositories.DatabaseRepository;


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

//	@Autowired private ApplicationContext applicationContext;
	@Autowired private DatabaseRepository databaseRepository;
	
	@Before
	public void setupDB() {
		this.databaseRepository.deleteAll();
		this.databaseRepository.save(new DatabaseEntity(1, "John", "D", "street1", "Male", "123456789"));
		this.databaseRepository.save(new DatabaseEntity(2, "Jane", "A", "street2", "Female", "987654321"));
		this.databaseRepository.save(new DatabaseEntity(3, "Adam", "C", "street3", "Female", "987654321"));
		this.databaseRepository.save(new DatabaseEntity(4, "Bar", "street4", "582471693"));
		this.databaseRepository.save(new DatabaseEntity(5, "Eel", "street5", "741852963"));
		this.databaseRepository.save(new DatabaseEntity(6, "Foo", "street6", "741852963"));
	}
	
	/*
	 * Tests
	 */
	@Test
	public void areAllRecordsFound() {
		int pageSize = 2;
		int page = 0;
		
		Pageable pageable = PageRequest.of(page, pageSize); 
		Page<DatabaseEntity> pageDB = databaseRepository.findAll(pageable);
		long nrItems = pageDB.getTotalElements();
		Assert.assertEquals(6, nrItems);
	}
	
	@Test
	public void isJsonPersonCorrect() {
		int pageSize = 6;
		int page = 0;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.ASC, "id"); 
		Page<DatabaseEntity> pageDB = databaseRepository.findAll(pageable);
		String jsonPerson = pageDB.getContent().get(0).toJson();
		String expectedJson = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"D\",\"address\":\"street1\",\"gender\":\"Male\",\"phoneNumber\":\"123456789\"}";
		Assert.assertEquals(expectedJson, jsonPerson);
	}
	
	@Test
	public void isJsonCompanyCorrect() {
		int pageSize = 6;
		int page = 0;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.ASC, "id"); 
		Page<DatabaseEntity> pageDB = databaseRepository.findAll(pageable);
		String jsonCompany = pageDB.getContent().get(4).toJson();
		String expectedJson = "{\"id\":5,\"companyName\":\"Eel\",\"address\":\"street5\",\"phoneNumber\":\"741852963\"}";
		Assert.assertEquals(expectedJson, jsonCompany);
	}
	
	@Test
	public void doesSearchInAllFieldsFindAll() {
		int pageSize = 6;
		int page = 0;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.ASC, "id"); 
		Page<DatabaseEntity> pageDB = databaseRepository.findInAllFields("street", pageable);
		long nrItems = pageDB.getTotalElements();
		Assert.assertEquals(6, nrItems);
	}
	
	@Test
	public void doesSearchInAllFieldsFindSingle() {
		int pageSize = 6;
		int page = 0;
		
		Pageable pageable = PageRequest.of(page, pageSize, Direction.ASC, "id"); 
		Page<DatabaseEntity> pageDB = databaseRepository.findInAllFields("joh", pageable);
		long nrItems = pageDB.getTotalElements();
		Assert.assertEquals(1, nrItems);
	}
	

	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackageClasses = {DatabaseRepository.class})
	static class PersonRepoTestConfiguration extends AbstractMongoConfiguration {
	    @Override
	    protected String getDatabaseName() {return "demo-test";}
		
	    @Bean
	    @Override
		public MongoClient mongoClient() {return new Fongo("mongo-test").getMongo();}
		
	    @Override
	    protected String getMappingBasePackage() {return "nl.sogyo.assessment";}
	}
}
