package com.jdbcconnectivity.pollutionmanagement.controller;

import com.jdbcconnectivity.pollutionmanagement.dao.IndoorResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.IndoorResultsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorResults;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorResultService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.IndoorReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.IndoorResultServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class IndoorResultsController {

	
public static String fields[]= {" Result Id "," Reading Id "," Category Name ","   User Id  ","   Value   ","          Status          ","     Area     "};
	
	private  IndoorResultService resultService;
	public IndoorResultsController()
	{
		//to initialize service
		this.resultService = new IndoorResultServiceImpl();
	}
	

	
	public void registerUI(String userId,int pm2_5value,int co2value,int coValue,String area) {
		
		//setting the name of category
		String category="Indoor";
		
		//getting the reading id from indoor readings service impl
		int readingId=IndoorReadingsServiceImpl.readingID;
		
		//creating object of indoor results DAO
		IndoorResultsDAO ar=new IndoorResultsDAOImpl();
		
		//setting initial status to be null
		String status=null;
		
		//calculating value from pm2.5 , co2 and co value
		double value=ar.calculateValue(pm2_5value, co2value, coValue);
		
		//getting status using value
		status=ar.getStatus(value);
		
		//adding result to indoor results table
		resultService.addResult(new IndoorResults(readingId,category,userId,value,status,area));
	
   

	
	}

}
