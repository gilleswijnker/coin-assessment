package nl.sogyo.assessment.repositories.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import nl.sogyo.assessment.domain.DataEntity;

public final class QueryCreator {
	public final static String queryAllString = queryAll();
	
	public final static String queryAll() {
		String qField = "{%s: {$regex: %s, $options: 'i'}}";
		List<String> qList = new ArrayList<>();
		
		Class<DataEntity> dbClass = DataEntity.class;
		for (Field field: dbClass.getDeclaredFields())
			if (field.getAnnotation(IQueryAll.class) != null)
				qList.add(String.format(qField, field.getName(), "?0"));
		return "{$or: [" + String.join(",", qList) + "]}";
	}
	
	public static String queryAll(String query) {
		String qField = "{'%s': {'$regex': '%s'}}";
		List<String> qList = new ArrayList<>();
		
		Class<DataEntity> dbClass = DataEntity.class;
		for (Field field: dbClass.getDeclaredFields())
			if (field.getAnnotation(IQueryAll.class) != null)
				qList.add(String.format(qField, field.getName(), query));
		return "{$or: [" + String.join(",", qList) + "]}";
	}
}
