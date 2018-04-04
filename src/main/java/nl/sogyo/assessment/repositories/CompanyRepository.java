package nl.sogyo.assessment.repositories;

import java.util.List;
import nl.sogyo.assessment.domainentities.Company;
import nl.sogyo.assessment.domainentities.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CompanyRepository extends MongoRepository<Company, String> {
	// spring automagically links this to the firstName-field of Person
	@Query
	public List<Company> findByCompanyName(String companyName);
	
	// db.assessment.createIndex({"$**": "text"})
	// this allows searching in any field
	@Query(value = "{$and: [{$text: {$search: '?0'}}, {'companyName': {$exists: true}}]}")
	public List<Company> findInAllFields(String any);
	
	@Override
	@Query(value = "{'companyName': {$exists: true}}")
	public List<Company> findAll();
	
	@Override
	@Query(value = "{'companyName': {$exists: true}}")
	public List<Company> findAll(Sort sort);
	
	@Override
	@Query(value = "{'companyName': {$exists: true}}")
	public Page<Company> findAll(Pageable pageable);
}
