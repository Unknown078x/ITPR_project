package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.IndoorResults;

public interface IndoorResultsDAO {

	/*---- to insert data into the table ----*/
	int save(IndoorResults indoorResult);
	
	/*--- to fetch complete Results list ---*/
	ArrayList<IndoorResults> findAll();
	
	//method to delete the Result
	int delete(int resultId);
	
	//method to delete the Result using reading id
	int deleteByReadingId(int readingId);
	
	//method to delete the Reading by user id
		int deleteByUserId(String userId);
	
	/*--- to fetch Result details based on Result id ----*/
	Object[] findByResultId(int resultId);
	
	/*--- to fetch Result details based on User id ----*/
	Object[] findByUserId(String userId);
	
	//method to calculate value
	double calculateValue(int pm2_5value,int co2Value,int coValue);
		
	//method to get status 
	String getStatus(double value);

	
	/*--- to fetch Result details based on Category Name and Area----*/
	Object[] findByCategoryNameAndArea(String categoryName,String area);
}
