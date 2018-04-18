package nl.sogyo.assessment;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;

import nl.sogyo.assessment.domain.DataEntity;
import nl.sogyo.assessment.repositories.DataRepository;
import nl.sogyo.assessment.repositories.helper.ParseQuery;


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

	@Autowired private DataRepository databaseRepository;
	
	@Before
	public void setupDB() {
		this.databaseRepository.deleteAll();
		this.databaseRepository.save(new DataEntity(1, "John", "D", "street1", "Male", "123456789"));
		this.databaseRepository.save(new DataEntity(2, "Jane", "A", "street2", "Female", "987654321"));
		this.databaseRepository.save(new DataEntity(3, "Adam", "C", "street3", "Female", "987654321"));
		this.databaseRepository.save(new DataEntity(4, "Bar", "street4", "582471693"));
		this.databaseRepository.save(new DataEntity(5, "Eel", "street5", "741852963"));
		this.databaseRepository.save(new DataEntity(6, "Foo", "street6", "741852963"));
	}
	
	private final int pageSize = 6;
	private final int page = 0;
	private final Pageable pageable = PageRequest.of(page, pageSize, Direction.ASC, "id"); 
	
	/*
	 * Tests
	 */
	@Test
	public void doesSearchInAllFieldsFindSingle() {
		this.testSearchHelper("joh", 1);
	}
	
	@Test
	public void doesSearchInAllFieldsFindAll() {		
		this.testSearchHelper("street", 6);
	}
	
	@Test
	public void doesSearchInAllFieldsFindNone() {
		this.testSearchHelper("does_not_exist", 0);
	}
	
	private void testSearchHelper(final String searchTerm, final int expectedItems) {
		Page<DataEntity> pageDB = databaseRepository.findInAllFields(searchTerm, pageable);
		long nrItems = pageDB.getTotalElements();
		Assert.assertEquals(expectedItems, nrItems);
	}
	
	@Test
	public void isJsonPersonCorrect() {
		Page<DataEntity> pageDB = databaseRepository.findInAllFields(".", pageable);
		String jsonPerson = pageDB.getContent().get(0).toJson();
		String expectedJson = "{\"id\":1,"
				+ "\"firstName\":\"John\","
				+ "\"lastName\":\"D\","
				+ "\"address\":\"street1\","
				+ "\"gender\":\"Male\","
				+ "\"phoneNumber\":\"123456789\","
				+ "\"personOrCompany\":\"person\"}";
		Assert.assertEquals(expectedJson, jsonPerson);
	}
	
	@Test
	public void isJsonCompanyCorrect() {
		Page<DataEntity> pageDB = databaseRepository.findInAllFields(".", pageable);
		String jsonCompany = pageDB.getContent().get(4).toJson();
		String expectedJson = "{\"id\":5,"
				+ "\"companyName\":\"Eel\","
				+ "\"address\":\"street5\","
				+ "\"phoneNumber\":\"741852963\","
				+ "\"personOrCompany\":\"company\"}";
		Assert.assertEquals(expectedJson, jsonCompany);
	}
	
	@Test
	public void doesSearchOnMultipleCriteriaWork() {
		this.testSearchHelper(ParseQuery.parse("john street1"), 1);
	}
	
	/*
	 * setup mock mongodb
	 */
	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackageClasses = {DataRepository.class})
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
