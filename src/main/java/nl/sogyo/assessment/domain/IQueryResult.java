package nl.sogyo.assessment.domain;

public interface IQueryResult {
	public long getTotalElements();
	public int getPageNumber();
	public int getTotalPages();
	public String getResult();
}
