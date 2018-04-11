package nl.sogyo.assessment.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import nl.sogyo.assessment.domain.DataEntity;

@Repository
public interface DataRepository extends MongoRepository<DataEntity, String>{
	@Query(value = "{$or: ["
			+ "{firstName: {$regex: ?0, $options: 'i'}},"
			+ "{lastName: {$regex: ?0, $options: 'i'}},"
			+ "{companyName: {$regex: ?0, $options: 'i'}},"
			+ "{address: {$regex: ?0, $options: 'i'}},"
			+ "{gender: {$regex: ?0, $options: 'i'}},"
			+ "{phoneNumber: {$regex: ?0}}]}"
	)
	public Page<DataEntity> findInAllFields(String value, Pageable pageable);
}
