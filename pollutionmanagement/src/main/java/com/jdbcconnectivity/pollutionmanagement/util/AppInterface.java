package com.jdbcconnectivity.pollutionmanagement.util;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.jdbcconnectivity.pollutionmanagement.controller.AirReadingsController;
import com.jdbcconnectivity.pollutionmanagement.controller.CategoryController;
import com.jdbcconnectivity.pollutionmanagement.controller.IndoorReadingsController;
import com.jdbcconnectivity.pollutionmanagement.controller.NoiseReadingsController;
import com.jdbcconnectivity.pollutionmanagement.controller.UserController;
import com.jdbcconnectivity.pollutionmanagement.services.AirReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.AirResultService;
import com.jdbcconnectivity.pollutionmanagement.services.CategoryService;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorResultService;
import com.jdbcconnectivity.pollutionmanagement.services.NoiseReadingsService;
import com.jdbcconnectivity.pollutionmanagement.services.NoiseResultService;
import com.jdbcconnectivity.pollutionmanagement.services.UsersService;
import com.jdbcconnectivity.pollutionmanagement.services.impl.AirReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.AirResultServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.CategoryServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.IndoorReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.IndoorResultServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.NoiseReadingsServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.NoiseResultServiceImpl;
import com.jdbcconnectivity.pollutionmanagement.services.impl.UsersServiceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AppInterface {

	
	//creating the object for scanner to take input from user
    private static final Scanner sc = new Scanner(System.in);
	//--------------------------------------------------------------------
	//creating objects of Controller module
    private static final CategoryController categoryController =
            new CategoryController();
    private static final UserController userController =
            new UserController();
    private static final AirReadingsController airReadingController =
            new AirReadingsController();
    private static final IndoorReadingsController indoorReadingController =
            new IndoorReadingsController();
    private static final NoiseReadingsController noiseReadingController =
            new NoiseReadingsController();
    
	//--------------------------------------------------------------------
	//creating objects of Service module
    private static final CategoryService categoryService =
            new CategoryServiceImpl();
    private static final UsersService userService =
            new UsersServiceImpl();
    private static final  AirReadingsService airReadingService =
            new AirReadingsServiceImpl();
    private static final IndoorReadingsService indoorReadingService =
            new IndoorReadingsServiceImpl();
    private static final NoiseReadingsService noiseReadingService =
            new NoiseReadingsServiceImpl();
    private static final  AirResultService airResultService =
            new AirResultServiceImpl();
    private static final IndoorResultService indoorResultService =
            new IndoorResultServiceImpl();
    private static final NoiseResultService noiseResultService =
            new NoiseResultServiceImpl();

	
	//creating method for Admin Menu
    static void adminMenu(String user) {
		//getting the name of user when logged in
    	String fullName=getName(user);
        while (true) {
            ConsoleUtil.clearScreen();
            System.out.println("");
            System.out.println("                                ===========================================================================================");
            System.out.println("                                                                 ∴ Welcome Back, "+fullName+" ∴");
            System.out.println("                                ===========================================================================================");
            System.out.println("");
            System.out.println("1. Add Category");
            System.out.println("2. See Categories");
            System.out.println("3. See all Users");
            System.out.println("4. Delete User");
            System.out.println("5. See All Readings by Category name");
            System.out.println("6. Delete Reading of any Category by Reading Id");
            System.out.println("7. Results by Category Name");
            System.out.println("8. Results by Category Name & Area");
            System.out.println("9. Change Password by User Id");
            System.out.println("0. Back to MAIN MENU");
            System.out.print("Choose option: ");
			
            String input = sc.nextLine();

			// checking if user gives the choice in numbers only
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter number only.");
                ConsoleUtil.pause(sc);
                continue;
            }

            int choice = Integer.parseInt(input);

			//using switch statement to switch the operation according to the choice given by user
            switch (choice) {
                case 1:
					//calling the register method using the category controller object
                    categoryController.registerUI(sc);
                    ConsoleUtil.pause(sc);
                    break;

                case 2:
					//calling the categoryList method using the category service object
                	categoryService.getCategoryList();
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 3:
                	//printing all the users
					userService.getUserList();
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 4:
					//getting user id from user
                	System.out.print("Enter User Id:");
                	String userId=sc.nextLine();
					//calling the delete method using the user service object
                	userService.deleteUser(userId);
                	//deleting all the data given by the user
                	airReadingService.deleteByUserId(userId);
                	indoorReadingService.deleteByUserId(userId);
                	noiseReadingService.deleteByUserId(userId);
                	airResultService.deleteByUserId(userId);
                	noiseResultService.deleteByUserId(userId);
                	indoorResultService.deleteByUserId(userId);
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 5:
					//getting the category name from user
                	System.out.print("Enter Category Name:");
                	String category=sc.nextLine();
					//checking the category to call the method according to the category
                	if("air".equalsIgnoreCase(category)) {
                		airReadingService.getReadingList();
                	}
                	else if("indoor".equalsIgnoreCase(category)) {
                		indoorReadingService.getReadingList();
                	}
                	else if("noise".equalsIgnoreCase(category)) {
                		noiseReadingService.getReadingList();
                	}
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 6:
					//getting category name from user
                	System.out.print("Enter Category Name:");
                	String categoryName=sc.nextLine();
                	int readingId;
                	if("air".equalsIgnoreCase(categoryName)) {
						//getting reading id from user
                		System.out.print("Enter reading id:");
                    	readingId=sc.nextInt();
                    	sc.nextLine();
                    	//deleting reading by reading id
                		airReadingService.delete(readingId);
                		//deleting results of the reading by reading id
                		airResultService.deleteByReadingId(readingId);
                	}
                	else if("indoor".equalsIgnoreCase(categoryName)) {
                		//getting reading id from user
                		System.out.print("Enter reading id:");
                    	readingId=sc.nextInt();
                    	sc.nextLine();
                    	
                		indoorReadingService.delete(readingId);
                		//deleting results of the reading by reading id
                		indoorResultService.deleteByReadingId(readingId);
                	}
                	else if("noise".equalsIgnoreCase(categoryName)) {
                		//getting reading id from user
                		System.out.print("Enter reading id:");
                    	readingId=sc.nextInt();
                    	sc.nextLine();
                    	//deleting reading by reading id
                		noiseReadingService.delete(readingId);
                		//deleting results of the reading by reading id
                		noiseResultService.deleteByReadingId(readingId);
                	}
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 7:
                	//getting category name from the user
                	System.out.print("Enter Category Name:");
                	String categoryN=sc.nextLine();
                	//checking if the given category matches or not
                	if("air".equalsIgnoreCase(categoryN)) {
                		airResultService.getResult();
                	}
                	else if("indoor".equalsIgnoreCase(categoryN)) {
                		indoorResultService.getResult();
                	}
                	else if("noise".equalsIgnoreCase(categoryN)) {
                		noiseResultService.getResult();
                	}
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 8:
                	//getting category name from user
                	System.out.print("Enter Category Name:");
                	String categoryNa=sc.nextLine();
                	//getting area from user
                	System.out.print("Enter Area :");
                	String area=sc.nextLine();
                	//checking if the given category matches or not
                	if("air".equalsIgnoreCase(categoryNa)) {
                		airResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	else if("indoor".equalsIgnoreCase(categoryNa)) {
                		indoorResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	else if("noise".equalsIgnoreCase(categoryNa)) {
                		noiseResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 9:
                	//getting user id from user
                	System.out.print("Enter User ID:");
                	String userID=sc.nextLine();
                	//getting new password from user
                	System.out.print("Enter New Password:");
                	String password=sc.nextLine();
                	//calling update password method from user service
                	userService.updatePassword(userID, password);
                	ConsoleUtil.pause(sc);
                    break;
                    
              
                case 0:
                	return;
                default:
                    System.out.println("Invalid option.");
                    ConsoleUtil.pause(sc);
            }
        }
    }
    
   


    
   public static void userMenu(String user) {
	   //getting name of the user when logged in
    	String fullName=getName(user);
        while (true) {
            ConsoleUtil.clearScreen();
            System.out.println("");
            System.out.println("                                ===========================================================================================");
            System.out.println("                                                                 ◌ Welcome Back, "+fullName+" ◌");
            System.out.println("                                ===========================================================================================");
            System.out.println("");
            System.out.println("1. Give Reading");
            System.out.println("2. See My Readings");
            System.out.println("3. See Categories");
            System.out.println("4. Result of My Readings");
            System.out.println("5. Result by Category name");
            System.out.println("6. Result by Category name & Area");
            System.out.println("7. Results of All Categories");
            System.out.println("8. Change Password");
            System.out.println("0. Back");

            System.out.print("Choose option: ");
            String input = sc.nextLine();
            
            //checking that user only select from given options using number only
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter number only.");
                ConsoleUtil.pause(sc);
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 : 
                		//giving choice to select the category
                		System.out.println("Choose Category:\n1.Air \n2.Indoor \n3.Noise ");
                		int ch=sc.nextInt();
                		sc.nextLine();
                		//checking the choice to call method to save reading
                		if(ch==1) {
                			airReadingController.registerUI(sc,user);
                		}
                		else if(ch==2) {
                			indoorReadingController.registerUI(sc,user);
                		}
                		else if(ch==3) {
                			noiseReadingController.registerUI(sc,user);
                		}
                		else {
                			System.out.print("Wrong Choice");
                		}
                		ConsoleUtil.pause(sc);
                		break;
				case 2 : 
					//giving choice to select the category
					System.out.print("Choose Category:\n1.Air \n2.Indoor \n3.Noise \n");
            		int ch1=sc.nextInt();
            		sc.nextLine();
            		//checking the choice to call method to get reading
            		if(ch1==1) {
            			airReadingService.getReadingsByUserId(user);
            		}
            		else if(ch1==2) {
            			indoorReadingService.getReadingsByUserId(user);
            		}
            		else if(ch1==3) {
            			noiseReadingService.getReadingsByUserId(user);
            		}
            		else {
            			System.out.print("Wrong Choice");
            		}
            		ConsoleUtil.pause(sc);
                		break;
				case 3 :
						//calling method to see all the categories
						categoryService.getCategoryList();
						ConsoleUtil.pause(sc);
                		break;
				case 4 : 
					//giving choice to select the category
					System.out.println("Choose Category:\n1.Air \n2.Indoor \n3.Noise \n");
            		int ch2=sc.nextInt();
            		sc.nextLine();
            		//checking the choice to call method to get Result
            		if(ch2==1) {
            			airResultService.getResultByUserId(user);
            		}
            		else if(ch2==2) {
            			indoorResultService.getResultByUserId(user);
            		}
            		else if(ch2==3) {
            			noiseResultService.getResultByUserId(user);
            		}
            		else {
            			System.out.print("Wrong Choice");
            		}
            		ConsoleUtil.pause(sc);
                		break;
				case 5 : 
					//getting category name
					System.out.println("Enter Category Name:");
					sc.nextLine();
                	String categoryN=sc.nextLine();
                	//checking the category name to see the result
                	if("air".equalsIgnoreCase(categoryN)) {
                		airResultService.getResult();
                	}
                	else if("indoor".equalsIgnoreCase(categoryN)) {
                		indoorResultService.getResult();
                	}
                	else if("noise".equalsIgnoreCase(categoryN)) {
                		noiseResultService.getResult();
                	}
                	ConsoleUtil.pause(sc);
                    break;
             
				case 6 : 
					//getting the category name from the user
					System.out.println("Enter Category Name:");
					sc.nextLine();
                	String categoryNa=sc.nextLine();
                	//getting the area from the user
                	System.out.println("Enter Area :");
                	String area=sc.nextLine();
                	//checking the category name to see result by category name and area
                	if("air".equalsIgnoreCase(categoryNa)) {
                		airResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	else if("indoor".equalsIgnoreCase(categoryNa)) {
                		indoorResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	else if("noise".equalsIgnoreCase(categoryNa)) {
                		noiseResultService.getResultByCategoryNameAndArea(categoryNa, area);
                	}
                	ConsoleUtil.pause(sc);
                    break;
				case 7 : 
					//calling methods to print the results of air, indoor and noise results
					airResultService.getResult();
					indoorResultService.getResult();
					noiseResultService.getResult();
					sc.nextLine();
					ConsoleUtil.pause(sc);
                		break;
				case 8 :
					//getting old password from user
					System.out.println("Enter Old Password:");
					String oldPass=sc.nextLine();
					//getting new password from the user
					System.out.println("Enter New Password:");
					String newPass=sc.nextLine();
					//calling method to change the password
					userService.updateOldPassword(user, oldPass, newPass);
					ConsoleUtil.pause(sc);
                		break;
				case 0 : { return; }
                		
				default : {
                    System.out.println("Invalid option");
                    ConsoleUtil.pause(sc);
                }
            }
        }
    }
    
    //method to check the credentials matched or not with the data from database
    static boolean checkLogin(String userID,String password) {
    	//initializing a new arrayList of type String
    	 ArrayList<String> userIdList = new ArrayList<>();
 		try (
 		        Connection con = DataBaseUtil.establishConnection();
 	            PreparedStatement ps = con.prepareStatement("select user_id from users");
 	            ResultSet rs = ps.executeQuery();
 		    ) {

             while (rs.next()) {
                 userIdList.add(rs.getString("user_id"));
             }

         } catch (Exception e) {
           System.out.print("Something went Wrong");
         }
 		//converting and adding the data from the user id arrayList
 		String[] userIds = userIdList.toArray(new String[0]);
 		
 		//check if the given user id exists in table or not
 		boolean foundId = false;
 		for (String id : userIds) {
             if (id.equals(userID)) {   
                 foundId = true;
                 break;
          }}
 		
 		//SQL statement
 		String selectSql = "SELECT password_hash FROM users WHERE user_id = ?";
 		boolean passMatch=false;
	    try (
	        Connection con = DataBaseUtil.establishConnection();
	        PreparedStatement selectStmt = con.prepareStatement(selectSql)
	    ) {
	        selectStmt.setString(1, userID);

	        try (ResultSet rs = selectStmt.executeQuery()) {
	        	//checking if no password found
	            if (!rs.next()) {
	               passMatch=false;
	               return false;
	            }

	            String storedHash = rs.getString("password_hash");
	            
	            //verifying if the passwords match or not
	            boolean matched = BCrypt.verifyer()
	                    .verify(password.toCharArray(), storedHash)
	                    .verified;

	            if (!matched) {
	            	return false;
	            }
	            else if(matched) {
	            	passMatch=true;
	            }
	        }
	        }catch(SQLException e) {
	        	 System.out.print("Something went Wrong");
	        }
	    
	    
	    
	  //checking if both id and password are correct or not
    	if(foundId && passMatch) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
    
    //method to check if the role given by user is correct or not
    static boolean checkRole(String userID,String role) {
    	  
    			String sql = "SELECT role FROM users WHERE user_id = ?";
    	 		boolean found=false;
    		    try (
    		        Connection con = DataBaseUtil.establishConnection();
    		        PreparedStatement selectStmt = con.prepareStatement(sql)
    		    ) {
    		        selectStmt.setString(1, userID);

    		        try (ResultSet rs = selectStmt.executeQuery()) {

    		            if (!rs.next()) {
    		               return false;
    		            }

    		            String storedRole = rs.getString("role");
    		           
    		           //comparing the given and stored role in database
    		           if(storedRole.equalsIgnoreCase(role)) {
    		        	   return true;
    		           }
    		           else {
    		        	   return false;
    		           }
    		        }
    		        }catch(SQLException e) {
    		        	 System.out.print("Something went Wrong");
    		        }
    		    //checking if role is matched or not
    		    if(found) {
    	    		return true;
    	    	}
    	    	else {
    	    		return false;
    	    	}
    }
    
    //method to get name by user id
    static String getName(String userId) {
        String sql = "SELECT full_name FROM users WHERE user_id = ?";
        String fullName = null;

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {                 
                    fullName = rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //returning full name
        return fullName;
    }

    //method to be executed 
    public static void run() {
    	

    	//creating a loop until the user exits
        while (true) {
            ConsoleUtil.clearScreen();
            System.out.println("                                ===========================================================================================");
            System.out.println("                                                                ✨ POLLUTION MANAGEMENT SYSTEM ✨");
            System.out.println("                                ===========================================================================================");
            System.out.println("");
            System.out.println("1. Login");
            System.out.println("2. SignUp");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String input = sc.nextLine();
            
            //ensuring that the user gives only numeric choice
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter number only.");
                ConsoleUtil.pause(sc);
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                	System.out.println("");
                	//getting user id
                	System.out.print("Enter User ID:");
                	String userId=sc.nextLine();
                	//getting password
                	System.out.print("Enter Password:");
                	String pass=sc.nextLine();
                	//getting role
                	System.out.print("Enter Role:");
                	String role=sc.nextLine();
                	//checking if the role matches or not
                	boolean roleMatch= checkRole(userId,role);
                	//checking if the user id and password matches or not
                	boolean match=checkLogin(userId,pass);
                	if(match) {
                		//checking if role is admin and credentials are correct
                		if("admin".equalsIgnoreCase(role) && roleMatch) {
                			adminMenu(userId);	
                		}
                		//checking if role is user and credentials are correct
                		else if("user".equalsIgnoreCase(role) && roleMatch) {
                			userMenu(userId);
                		}
                		else {
                			System.out.print("Invalid Role...");
                			ConsoleUtil.pause(sc);
                			continue;
                		}
                	}
                	else {
                		System.out.print("Invalid User Id or Password...");
                		ConsoleUtil.pause(sc);
                		continue;
                	}
                    break;

                case 2:
                	//registering a new user into the database
                	 System.out.println("");
                	userController.registerUserUI(sc);
                	ConsoleUtil.pause(sc);
                    break;
                    
                case 0:
                	//exiting from the code
                	ConsoleUtil.clearScreen();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    ConsoleUtil.pause(sc);
            }
        }
    }
}

