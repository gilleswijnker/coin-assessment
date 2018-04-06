package nl.sogyo.assessment;

import nl.sogyo.assessment.domain.DataNavigator;
import nl.sogyo.assessment.domain.DatabaseEntity;
import nl.sogyo.assessment.repositories.DatabaseRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class MongoSpringTest implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(MongoSpringTest.class, args);
	}
	
	@Autowired
	BeanFactory beanFactory;
	
	@Autowired
	DatabaseRepository databaseRepository;
	
	@Autowired
	DataNavigator dataNavigator;
	
	@Override
	public void run(String... args) {
		DataNavigator dn = beanFactory.getBean(DataNavigator.class, "itter");
//		DataNavigator dn = new DataNavigator("itter");
		System.out.println(dn.getTotalElements());
//		//
//		int pageSize = 2;
//		int page = 0;
//		
//		Pageable pageable = PageRequest.of(page, pageSize); 
//		Page<DatabaseEntity> pageDB = databaseRepository.findAll(pageable);
//		long nrItems = pageDB.getTotalElements();
//		System.out.println(nrItems);
//		//
//		System.out.println(dataNavigator.getTotalElements());
	}
}
