package com.jdbcconnectivity.pollutionmanagement.controller;

import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.model.AirReadings;
import com.jdbcconnectivity.pollutionmanagement.services.AirReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.AirReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class AirReadingsController {

	public static String fields[]= {"  Reading ID  ","    User ID    ","    PM 2.5 Value    ","   PM 10 Value   ","     Locality     "};
	
	
	private  AirReadingsService readingService;
	public AirReadingsController()
	{
		//to initialize service
		this.readingService = new AirReadingsServiceImpl();
	}
	
	//method to print the data given by user for confirmation
	public static void printAll() {
		String field[]= {"    User ID    ","    PM 2.5 Value    ","   PM 10 Value   ","     Locality     "};
		
		
		//calling method to print border
		PrintUtil.printBorder(field);
		//calling method to print fields
		PrintUtil.printFields(field);
		
		PrintUtil.printBorder(field);
		//calling method to print multiple values
		PrintUtil.printMultipleValues(field,PrintUtil.datalist);
		
		PrintUtil.printBorder(field);
		
	}
	
	//creating object of Air Results Controller
	AirResultsController ar=new AirResultsController();

	public void registerUI(Scanner sc,String userId) {
	       
        
		//taking value of PM2.5 Value from user
        System.out.print("Enter PM 2.5 value: ");
        int pm2_5Value= sc.nextInt();
        
        //taking value of PM10 value from user
        System.out.print("Enter PM 10 value: ");
        int pm10Value = sc.nextInt();
        sc.nextLine();		//consume leftover
        
        //taking locality from user
        System.out.print("Enter your Locality: ");
        String locality = sc.nextLine();

        //adding the data into an object to show the data
        Object arr[]= { userId, pm2_5Value, pm10Value, locality };
        PrintUtil.getData(arr);
        printAll();
        
        //confirming details
        System.out.print("Confirm details (y | n): ");
        String choice=sc.nextLine();
        
        if ("y".equalsIgnoreCase(choice)) {
            readingService.takeReading(new AirReadings(userId, pm2_5Value, pm10Value, locality));
            //calculating and adding data into Air results table
            ar.registerUI(userId,pm2_5Value,pm10Value,locality);
        }
        else if("n".equalsIgnoreCase(choice)) {
        	System.out.println("Reading not Saved...");
        }
        else {
        	System.out.println("Invalid choice");
        }

	
	}
}
