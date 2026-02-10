package com.jdbcconnectivity.pollutionmanagement.services;

import com.jdbcconnectivity.pollutionmanagement.model.AirResults;

public interface AirResultService {


			//method to add a new result
			void addResult(AirResults result);
			//method to delete Result
			void delete(int resultId);
			//method to delete the Result using reading id
			void deleteByReadingId(int readingId);
			//method to delete the Reading by user id
			void deleteByUserId(String userId);
			//method to fetch all Results
			void getResult();
			//method to fetch Result details by result id
			void getResultByResultId(int resultId);
			//method to fetch Result details by user ID 
			void getResultByUserId(String userId);
			//method to fetch Result details by Category name and area
			void getResultByCategoryNameAndArea(String categoryName,String area);
			
}
