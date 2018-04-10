package nl.sogyo.assessment.domain;

import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import nl.sogyo.assessment.repositories.DataRepository;

@Service
public class DataNavigator {
	@Autowired
	private DataRepository databaseRepository;
	
	private DataNavigator() {}	
	
	public IDataNavigator executeQuery(final String query) {
		return this.executeQuery(query, 1, 10);
	}
	
	public IDataNavigator executeQuery(final String query, final int page, final int pageSize) {
		return new innerDBNav(query, page, pageSize);
	}
	
	// inner class to force the use of executeQuery: ensures 'dbPage' is not null
	private class innerDBNav implements IDataNavigator{
		private Page<DataEntity> dbPage = null;
		
		public innerDBNav(final String query, final int page, final int pageSize) {
			Pageable pageable = PageRequest.of(page - 1, pageSize);
			this.dbPage = databaseRepository.findInAllFields(query, pageable);
		}
		
		@Override
		public long getTotalElements() {
			return this.dbPage.getTotalElements();
		}
		
		@Override
		public int getPageNumber() {
			return dbPage.getNumber() + 1;
		}
		
		@Override
		public int getTotalPages() {
			return dbPage.getTotalPages();
		}
		
		@Override
		public String getResult() {
			StringJoiner jsonRepresentation = new StringJoiner(",", "[", "]");
			for (DataEntity dbEntity: dbPage) {
				jsonRepresentation.add(dbEntity.toJson());
			}
			return jsonRepresentation.toString();
		}
	}
}
