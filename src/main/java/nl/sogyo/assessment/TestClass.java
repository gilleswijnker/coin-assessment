package nl.sogyo.assessment;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import nl.sogyo.assessment.repositories.helper.QueryCreator;

public class TestClass {
	public static void main(String args[]) {
		System.out.println(QueryCreator.queryAll());
	}
}