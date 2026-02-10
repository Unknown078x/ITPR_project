package com.jdbcconnectivity.pollutionmanagement.controller;


import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.model.Users;
import com.jdbcconnectivity.pollutionmanagement.services.UsersService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.UsersServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class UserController {
	public static String fields[]= {"  User ID  ","    UserName    ","      Full Name      ","       Area       ","  Role  "};
	private UsersService userService;
	public UserController()
	{
		//to initailize service
		this.userService = new UsersServiceImpl();
	}
	
	
	public static void printAll() {
		
		
		//calling method to print border
		PrintUtil.printBorder(fields);
		//calling method to print fields
		PrintUtil.printFields(fields);
		
		PrintUtil.printBorder(fields);
		//calling method to print multiple values
		PrintUtil.printMultipleValues(fields,PrintUtil.datalist);
		
		PrintUtil.printBorder(fields);
		
	}
	
	
	public void registerUserUI(Scanner sc)
	{
       
        

            System.out.print("Enter User ID: ");
            String userId = sc.nextLine();

            System.out.print("Enter UserName: ");
            String userName = sc.nextLine();

            System.out.print("Enter your Password: ");
            String password = sc.nextLine();

            System.out.print("Enter your Full Name: ");
            String fullName = sc.nextLine();

            System.out.print("Enter your Area: ");
            String area = sc.nextLine();

            System.out.print("Enter your Role (admin|user): ");
            String role = sc.nextLine();
            
            if("admin".equalsIgnoreCase(role)) {
            	System.out.print("Enter Admin Unicode: ");
            	int unicode=sc.nextInt();
            	sc.nextLine();
            	if(!(unicode==2222)) {
            		System.out.println("Wrong Unicode");
            		System.out.println("Now You will be registered as User: ");
            		role="user";
            	}
            }

            Object arr[] = { userId, userName, fullName, area, role };
            PrintUtil.getData(arr);
            printAll();
            arr=null;

            System.out.print("Confirm details (y | n): ");
            String choice=sc.nextLine();
           
            
            if ("y".equalsIgnoreCase(choice)) {
                try {
					userService.registerUser(new Users(userId, userName, password, fullName, area, role));
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if("n".equalsIgnoreCase(choice)) {
            	System.out.println("Not registered...");
            }
            else {
            	System.out.println("Invalid choice");
            }
            
   
		

		
	}

}
