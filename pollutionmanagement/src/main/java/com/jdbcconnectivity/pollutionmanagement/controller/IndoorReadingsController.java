package com.jdbcconnectivity.pollutionmanagement.controller;

import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.model.IndoorReadings;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.IndoorReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class IndoorReadingsController {

public static String fields[]= {"  Reading ID  ","    User ID    ","    PM 2.5 Value    ","   CO 2 Value   ","   CO Value   ","     Locality     "};
	
	private  IndoorReadingsService readingService;
	public IndoorReadingsController()
	{
		//to initialize service
		this.readingService = new IndoorReadingsServiceImpl();
	}
	
	//method to print the data given by user for confirmation
	public static void printAll() {
		String field[]= {"    User ID    ","    PM 2.5 Value    ","   CO 2 Value   ","   CO Value   ","     Locality     "};
		
		
		//calling method to print border
		PrintUtil.printBorder(field);
		//calling method to print fields
		PrintUtil.printFields(field);
		
		PrintUtil.printBorder(field);
		//calling method to print multiple values
		PrintUtil.printMultipleValues(field,PrintUtil.datalist);
		
		PrintUtil.printBorder(field);
		
	}
	
	//creating object of Indoor Results Controller
	IndoorResultsController ir=new IndoorResultsController();

	public void registerUI(Scanner sc,String userId) {
	       
        //taking PM2.5 value from user
        System.out.print("Enter PM 2.5 value: ");
        int pm2_5Value = sc.nextInt();

        //taking CO2 value from user
        System.out.print("Enter CO 2 value: ");
        int co2Value = sc.nextInt();
        
        //taking CO value from user
        System.out.print("Enter CO value: ");
        int coValue = sc.nextInt();
        sc.nextLine();

        //taking locality from user
        System.out.print("Enter your Locality: ");
        String locality = sc.nextLine();

      
        //adding the data into an object to show the data
        Object arr[] = { userId, pm2_5Value,co2Value, coValue, locality };
        PrintUtil.getData(arr);
        printAll();

        //confirming details
        System.out.print("Confirm details (y | n): ");
        String choice=sc.nextLine();
        
        
        if ("y".equalsIgnoreCase(choice)) {
            readingService.takeReading(new IndoorReadings( userId, pm2_5Value, co2Value,coValue, locality));
          //calculating and adding data into Indoor results table
            ir.registerUI(userId, pm2_5Value, co2Value, coValue, locality);
        }
        
        else if("n".equalsIgnoreCase(choice)) {
        	System.out.println("Reading not Saved...");
        }
        else {
        	System.out.println("Invalid choice");
        }
	
	}
}
