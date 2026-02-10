package com.jdbcconnectivity.pollutionmanagement.services;

import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;

public interface NoiseReadingsService {
		//method to take pollution reading
			void takeReading(NoiseReadings reading);
			
			//method to delete Reading
			void delete(int readingId);
			
			//method to delete the Reading by user id
			void deleteByUserId(String userId);
			
			//method to fetch all readings
			void getReadingList();
			
			//method to fetch Reading details of a particular reading
			void getReadingDetails(int readingId);
			
			//method to get Readings of a particular user
			void getReadingsByUserId(String userId);
}
