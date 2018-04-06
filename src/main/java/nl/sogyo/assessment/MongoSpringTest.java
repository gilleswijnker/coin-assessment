package nl.sogyo.assessment;

import nl.sogyo.assessment.domain.DataNavigator;
import nl.sogyo.assessment.domain.DataEntity;
import nl.sogyo.assessment.domain.IDataNavigator;
import nl.sogyo.assessment.repositories.DataRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;

//@SpringBootApplication
public class MongoSpringTest implements CommandLineRunner{
	public static void main(String[] args) {
		System.out.println("\n\n\n===================\n\n\nMAIN\n\n\n===================");
		SpringApplication.run(MongoSpringTest.class, args);
	}
	
	@Autowired
	DataNavigator dataNavigator;
	
	@Override
	public void run(String... args) {
		IDataNavigator dn = dataNavigator.executeQuery("itter", 0, 10);
		System.out.println(dn.getTotalElements());
		System.out.println(dn.toJson());
//		Integer[] l = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
//		Pageable pageable = PageRequest.of(0, 5);
//		Page<Integer> page = new PageImpl<Integer>(Arrays.asList(l), pageable, 16);
//		
//		System.out.println(page.getNumberOfElements());
//		System.out.println(page.getNumber());
//		System.out.println(page.getSize());
//		System.out.println(page.getContent());
	}
}
