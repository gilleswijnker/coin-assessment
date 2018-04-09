package nl.sogyo.assessment.domain;

public interface IDataNavigator {
	public long getTotalElements();
	public int getPageNumber();
	public String toJson();
}
