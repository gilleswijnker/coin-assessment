package nl.sogyo.assessment;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import nl.sogyo.assessment.domain.DataNavigator;
import nl.sogyo.assessment.domain.DataEntity;
import nl.sogyo.assessment.domain.IDataNavigator;
import nl.sogyo.assessment.repositories.DataRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DataRepository.class, DataNavigator.class})
public class DataNavigatorTest {
	@MockBean DataRepository databaseRepositoryMock;
	
	@Autowired
	DataNavigator dataNavigator;
	
	@Mock
	Page<DataEntity> dbPageMock;
	
	private final String TEST_QUERY= "test";
	private final long NUMBER_OF_ELEMENTS = 6L;
	
	@Before
	public void setupMockRepository() {
		Mockito.when(dbPageMock.getTotalElements()).thenReturn(NUMBER_OF_ELEMENTS);
		Mockito.when(databaseRepositoryMock.findInAllFields(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(dbPageMock);
	}
	
	@Test
	public void doesNavigatorSuccesfullyInitializeInnerObjects() {
		IDataNavigator dn = dataNavigator.executeQuery(TEST_QUERY, 1, 10);
		long total = dn.getTotalElements();
		Assert.assertEquals(NUMBER_OF_ELEMENTS, total);
	}
	
	@Test
	public void doesNavigatorCreateCorrectJsonOfElements() {
		ArrayList<DataEntity> list = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			list.add(new DataEntity(i, null, null, null));
		}
		Mockito.when(dbPageMock.iterator()).thenReturn(list.iterator());
		
		IDataNavigator dn = dataNavigator.executeQuery(TEST_QUERY, 1, 10);
		String json = dn.getResult();
		Assert.assertEquals("[{\"id\":1,\"personOrCompany\":\"person\"},"
				+ "{\"id\":2,\"personOrCompany\":\"person\"},"
				+ "{\"id\":3,\"personOrCompany\":\"person\"},"
				+ "{\"id\":4,\"personOrCompany\":\"person\"},"
				+ "{\"id\":5,\"personOrCompany\":\"person\"}]"
			, json);
	}
}
