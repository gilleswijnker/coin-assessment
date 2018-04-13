package nl.sogyo.assessment;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import nl.sogyo.assessment.domain.DataEntity;
import nl.sogyo.assessment.repositories.DataRepository;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
public class IntegrationTest {
	/*
	 * Setup test environment
	 */
	@ClassRule
	public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();
	
    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("demo-test");

	@Autowired private DataRepository databaseRepository;
	
	@Autowired private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setupDB() {
		this.databaseRepository.deleteAll();
		this.databaseRepository.save(new DataEntity(1, "John", "D", "street1", "Male", "123456789"));
		this.databaseRepository.save(new DataEntity(2, "Jane", "A", "street2", "Female", "987654321"));
		this.databaseRepository.save(new DataEntity(3, "Adam", "C", "street3", "Female", "987654321"));
		this.databaseRepository.save(new DataEntity(4, "Bar", "street4", "582471693"));
		this.databaseRepository.save(new DataEntity(5, "Eel", "street5", "741852963"));
		this.databaseRepository.save(new DataEntity(6, "Jane", "street6", "741852963"));
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/*
	 * Test
	 */
	@Test
	public void isASingleResultCorrectlyReturned() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/query")
					.param("searchvalue", "john"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is(
					"[{\"id\":1,"
					+ "\"firstName\":\"John\","
					+ "\"lastName\":\"D\","
					+ "\"address\":\"street1\","
					+ "\"gender\":\"Male\","
					+ "\"phoneNumber\":\"123456789\","
					+ "\"personOrCompany\":\"person\"}]"
		)));
	}
	
	@Test
	public void areMultipleResultsCorrectlyFound() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/query")
					.param("searchvalue", "jane"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(2)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is(
					// person Jane
					"[{\"id\":2,"
					+ "\"firstName\":\"Jane\","
					+ "\"lastName\":\"A\","
					+ "\"address\":\"street2\","
					+ "\"gender\":\"Female\","
					+ "\"phoneNumber\":\"987654321\","
					+ "\"personOrCompany\":\"person\"},"
					// company Jane
					+ "{\"id\":6,"
					+ "\"companyName\":\"Jane\"," 
					+ "\"address\":\"street6\"," 
					+ "\"phoneNumber\":\"741852963\"," 
					+ "\"personOrCompany\":\"company\"}]"
		)));
	}
	
	/*
	 * setup mock mongodb
	 */
	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackages = {
			"nl.sogyo.assessment.controller",
			"nl.sogyo.assessment.domain",
			"nl.sogyo.assessment.repositories"
	})
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
