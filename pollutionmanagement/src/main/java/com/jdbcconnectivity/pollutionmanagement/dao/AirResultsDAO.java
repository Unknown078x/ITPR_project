package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.AirResults;

public interface AirResultsDAO {
	
	/*---- to insert data into the table ----*/
	int save(AirResults airResult);
	
	/*--- to fetch complete Results list ---*/
	ArrayList<AirResults> findAll();
	
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
	double calculateValue(double pm2_5value,double pm10value);
	
	//method to get status according to PM2.5 value
	String getStatus25(double value);
	
	//method to get status according to PM10 value
	String getStatus10(double value);
	
	/*--- to fetch Result details based on Category Name and Area----*/
	Object[] findByCategoryNameAndArea(String categoryName,String area);
}
