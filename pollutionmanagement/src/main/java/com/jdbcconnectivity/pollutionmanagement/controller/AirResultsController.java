package com.jdbcconnectivity.pollutionmanagement.controller;


import com.jdbcconnectivity.pollutionmanagement.dao.AirResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.AirResultsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.AirResults;
import com.jdbcconnectivity.pollutionmanagement.services.AirResultService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.AirReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.AirResultServiceImpl;

public class AirResultsController {

	
public static String fields[]= {" Result Id "," Reading Id "," Category Name ","   User Id  ","   Value   ","       Status       ","     Area     "};
	
	private AirResultService resultService;
	public AirResultsController()
	{
		//to initialize service
		this.resultService = new AirResultServiceImpl();
	}
	
	
	
	public void registerUI(String userId,int pm2_5value,int pm10value,String locality) {
		
		//setting category
		String category="air";
		
		//getting reading id from Air readings Service
		int readingId=AirReadingsServiceImpl.readingID;
		
		//creating object of Air result DAO Impl
		AirResultsDAO ar=new AirResultsDAOImpl();
		
		//setting initial status as null
		String status=null;
		
		//calculating value from PM2.5 and PM10 values
		double value=ar.calculateValue(pm2_5value, pm10value);
		
		//getting status according to PM2.5 or PM10 value
		if(value==pm2_5value) {
			status=ar.getStatus25(value);
		}
		else if(value==pm10value) {
			status =ar.getStatus10(value);
		}
		
		//adding result to air results table
		resultService.addResult(new AirResults(readingId,category,userId,value,status,locality));
	
	}
}
