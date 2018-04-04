package nl.sogyo.assessment.repositories;

import java.util.List;
import nl.sogyo.assessment.domainentities.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PersonRepository extends MongoRepository<Person, String> {
	// spring automagically links this to the firstName-field of Person
	@Query
	public List<Person> findByFirstName(String firstName);
	
	// define the mongo query to be performed when calling this method
	// ?0 is first param, ?1 is second param etc
	@Query(value = "{$or: [{'firstName': ?0}, {'lastName': ?0}]}")
	public List<Person> findByFirstNameOrLastName(String name);
	
	// db.assessment.createIndex({"$**": "text"})
	// this allows searching in any field
	@Query(value = "{$and: [{$text: {$search: '?0'}}, {'companyName': {$exists: false}}]}")
	public List<Person> findInAllFields(String any);
	
	@Override
	@Query(value = "{'companyName': {$exists: false}}")
	public List<Person> findAll();
	
	@Override
	@Query(value = "{'companyName': {$exists: false}}")
	public List<Person> findAll(Sort sort);
	
	@Override
	@Query(value = "{'companyName': {$exists: false}}")
	public Page<Person> findAll(Pageable pageable);
}
