package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.AirReadings;

public interface AirReadingsDAO {

	
	/*---- to insert data into the table ----*/
	int save(AirReadings AirData);
	
	/*--- to fetch complete Reading list ---*/
	ArrayList<AirReadings> findAll();
	
	//method to delete the Reading
	int delete(int readingId);
	
	//method to delete the Reading by user id
	int deleteByUserId(String userId);
	
	/*--- to fetch Reading details based on Reading id ----*/
	Object[] findByReadingId(int readingId);
	
	/*--- to fetch Reading details based on User id ----*/
	Object[] findByUserId(String userId);
}
