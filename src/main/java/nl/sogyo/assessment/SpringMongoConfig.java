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
		// note: 'mongo' is name of the Docker service where the mongo daemon lives
		// 		 This app needs this to find the daemon
        return new MongoClient("localhost");
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "coin");
    }
}