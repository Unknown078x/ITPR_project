package com.jdbcconnectivity.pollutionmanagement.controller;

import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.model.Category;
import com.jdbcconnectivity.pollutionmanagement.services.CategoryService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.CategoryServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class CategoryController {

public static String fields[]= {"  Category ID  ","    Category Name    ","        Description        "};
	
	private CategoryService categoryService;
	public CategoryController()
	{
		//to initialize service
		this.categoryService = new CategoryServiceImpl();
	}
	
	//method to print the data given by user for confirmation
	public static void printAll() {
		
		String field[]= {"    Category Name    ","        Description        "};
		//calling method to print border
		PrintUtil.printBorder(field);
		//calling method to print fields
		PrintUtil.printFields(field);
		
		PrintUtil.printBorder(field);
		//calling method to print multiple values
		PrintUtil.printMultipleValues(field,PrintUtil.datalist);
		
		PrintUtil.printBorder(field);
		
	}
	
	  public void registerUI(Scanner sc) {

		  	//taking category name from user
	        System.out.print("Enter Category Name: ");
	        String categoryName = sc.nextLine();

	        //taking description from user
	        System.out.print("Enter Description: ");
	        String description = sc.nextLine();

	      //adding the data into an object to show the data
	        Object[] arr = { categoryName, description };
	        PrintUtil.getData(arr);
	        printAll();

	        //confirming details
	        System.out.print("Confirm details (y | n): ");
	        String choice = sc.nextLine();

	        
	        if ("y".equalsIgnoreCase(choice)) {
	            try {
	                categoryService.registerCategory(new Category(categoryName, description));
	            } catch (Exception e) {
	                System.out.println("Error while saving category.");
	                e.printStackTrace();
	            }
	        }
	        else if ("n".equalsIgnoreCase(choice)) {
	            System.out.println("Category not saved.");
	        }
	        else {
	            System.out.println("Invalid choice.");
	        }
	        
	    }
}
