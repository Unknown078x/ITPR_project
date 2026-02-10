package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;

public interface NoiseReadingsDAO {

	
	/*---- to insert data into the table ----*/
	int save(NoiseReadings noiseData);
	
	/*--- to fetch complete Reading list ---*/
	ArrayList<NoiseReadings> findAll();
	
	//method to delete the Reading
	int delete(int readingId);
	
	//method to delete the Reading by user id
		int deleteByUserId(String userId);
	
	/*--- to fetch Reading details based on Reading id ----*/
	Object[] findByReadingId(int readingId);
	
	/*--- to fetch Reading details based on User id ----*/
	Object[] findByUserId(String userId);
}
