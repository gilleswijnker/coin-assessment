package nl.sogyo.assessment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

//@EnableReactiveMongoRepositories
@Configuration
public class SpringMongoConfig {

	@Bean
    public MongoClient mongo() throws Exception {
        return new MongoClient("localhost");
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "coin");
    }
}