package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.NoiseResults;

public interface NoiseResultsDAO {

	/*---- to insert data into the table ----*/
	int save(NoiseResults noiseResult);
	
	/*--- to fetch complete Results list ---*/
	ArrayList<NoiseResults> findAll();
	
	//method to delete the Result
	int delete(int resultId);
	
	//method to delete the Result using reading id
	int deleteByReadingId(int readingId);
	
	//method to delete the Reading by user id
		int deleteByUserId(String userId);
	
	/*--- to fetch Result details based on Result id ----*/
	Object[] findByResultId(int resultId);
	
	/*--- to fetch Result details based on user Id ----*/
	Object[] findByUserId(String userId);
	
	//method to get status according to sound level
	String getStatus(double soundLevel);
		
	
	/*--- to fetch Result details based on Category Name and Area----*/
	Object[] findByCategoryNameAndArea(String categoryName,String area);
}
