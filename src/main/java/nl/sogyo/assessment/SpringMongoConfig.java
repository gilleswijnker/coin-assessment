package nl.sogyo.assessment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories({"nl.sogyo.assessment.repositories"})
public class SpringMongoConfig {

	@Bean
    public MongoClient mongo() throws Exception {
        return new MongoClient("mongo");
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "coin");
    }
}