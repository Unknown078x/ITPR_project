package com.jdbcconnectivity.pollutionmanagement.controller;

import com.jdbcconnectivity.pollutionmanagement.dao.NoiseResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.NoiseResultsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.NoiseResults;
import com.jdbcconnectivity.pollutionmanagement.services.NoiseResultService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.NoiseReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.NoiseResultServiceImpl;

public class NoiseResultsController {

	
public static String fields[]= {" Result Id "," Reading Id "," Category Name ","   User Id  ","   Value   ","       Status       ","     Area     "};
	
	private  NoiseResultService resultService;
	public NoiseResultsController()
	{
		//to initialize service
		this.resultService = new NoiseResultServiceImpl();
	}
	

	public void registerUI(String userId,int soundLevel,String area) {
		
		//setting category name
		String category="Noise";
		
		//getting reading id from noise service impl
		int readingId=NoiseReadingsServiceImpl.readingID;
		
		//creating object of Noise results DAO impl
		NoiseResultsDAO ar=new NoiseResultsDAOImpl();
		
		//getting status from sound level
		String status =ar.getStatus(soundLevel);
	
		//adding result to the noise results table
		resultService.addResult(new NoiseResults(readingId,category,userId,soundLevel,status,area));
	
   

	
	}
	
}
