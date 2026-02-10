package com.jdbcconnectivity.pollutionmanagement.controller;

import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;
import com.jdbcconnectivity.pollutionmanagement.services.NoiseReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.NoiseReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class NoiseReadingsController {

public static String fields[]= {"  Reading ID  ","    User ID    ","    Sound Level    ","     Locality     "};
	
	private  NoiseReadingsService readingService;
	public NoiseReadingsController()
	{
		//to initialize service
		this.readingService = new NoiseReadingsServiceImpl();
	}

	//method to print the data given by user for confirmation
	public static void printAll() {
		String field[]= {"    User ID    ","    Sound Level    ","     Locality     "};
		
		
		//calling method to print border
		PrintUtil.printBorder(field);
		//calling method to print fields
		PrintUtil.printFields(field);
		
		PrintUtil.printBorder(field);
		//calling method to print multiple values
		PrintUtil.printMultipleValues(field,PrintUtil.datalist);
		
		PrintUtil.printBorder(field);
		
	}
	
	
	//creating object of Noise results controller
	NoiseResultsController nr=new NoiseResultsController();
	
	public  void registerUI(Scanner sc,String userId) {
	       
		//taking sound level from user
        System.out.print("Enter Sound Level: ");
        int soundLevel = sc.nextInt();
        sc.nextLine();

        //taking locality from user
        System.out.print("Enter your Locality: ");
        String locality = sc.nextLine();

        //adding the data into an object to show the data
        Object arr[] = { userId, soundLevel, locality };
        PrintUtil.getData(arr);
        printAll();

        //confirming details
        System.out.print("Confirm details (y | n): ");
        String choice=sc.nextLine();
        
        if ("y".equalsIgnoreCase(choice)) {
            readingService.takeReading(new NoiseReadings( userId, soundLevel, locality));
          //calculating and adding data into Noise results table
            nr.registerUI(userId, soundLevel, locality);
        } 
        
        else if("n".equalsIgnoreCase(choice)) {
        	System.out.println("Reading not Saved...");
        }
        else {
        	System.out.println("Invalid choice");
        }
        

	
	}
}
