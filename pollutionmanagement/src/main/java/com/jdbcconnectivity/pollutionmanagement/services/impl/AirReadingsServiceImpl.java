package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.AirReadingsController;
import com.jdbcconnectivity.pollutionmanagement.dao.AirReadingsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.AirReadingsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.AirReadings;
import com.jdbcconnectivity.pollutionmanagement.services.AirReadingsService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class AirReadingsServiceImpl implements AirReadingsService{

	private AirReadingsDAO airDAO;
	public static int readingID;
	public AirReadingsServiceImpl(){
		airDAO=new AirReadingsDAOImpl();
	}
	
	@Override
	public void takeReading(AirReadings reading) {
		//creating userId List of type ArrayList
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
	            e.printStackTrace();
	        }
			String[] userIds = userIdList.toArray(new String[0]);
			
			//check if the given user id exists in table or not
			boolean found = false;
			for (String id : userIds) {
	            if (id.equals(reading.getUserId())) {   
	                found = true;
	                break;
	         }}
		
			//checking the data is correct or not
		if(reading==null) {
			System.out.println("Reading data is empty");
		}
		else if(reading.getPm2_5value()<0) {
			System.out.println("PM 2.5 value cannot be negative");
		}
		else if(reading.getPm10value()<0) {
			System.out.println("PM 10 value cannot be negative");
		}
		else if(!found) {
			System.out.println("User ID doesn't exist");
		}
		else {
			//adding data into tables using DAO
			 readingID=airDAO.save(reading);
			if(readingID>0) {
				System.out.println("Reading saved successfully");
			}
			else {
				System.out.println("Unable to save Reading");
			}
		}
		
	}

	@Override
	public void delete(int readingId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_readings WHERE reading_id = ?";
		 try (
		            Connection con = DataBaseUtil.establishConnection();
		            PreparedStatement ps = con.prepareStatement(check)
		        ) {
		            ps.setInt(1, readingId);

		            try (ResultSet rs = ps.executeQuery()) {
		                found= rs.next();   // true = exists
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			
			
			
			if(!found) {
				System.out.println("Reading ID does NOT exist");
			}
			else {
				//adding data into tables
				int rows=airDAO.delete(readingId);
				if(rows>0) {
					System.out.println("Reading deleted successfully");
				}
				else {
					System.out.println("Unable to delete Reading");
				}
			}
		
	}

	@Override
	public void getReadingList() {
		// To fetch records from DAO layer
		ArrayList<AirReadings> readingList = airDAO.findAll();
		//changing reading object to string array
		ArrayList<String[]> dataList = new ArrayList<>();

		for (AirReadings u : readingList) {
		    dataList.add(PrintUtil.toStringArray(u));
		}
		
		
		if(readingList.size() > 0)
		{
			//getting the fields from the Air readings controller
			String[] fields=AirReadingsController.fields;
			//printing the data using printData method in PrintUtil
			PrintUtil.printData(fields, dataList);
		}
		else
		{
			System.out.println("No Reading data found");
		}
		
	}

	@Override
	public void getReadingDetails(int readingId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_readings WHERE reading_id = ?";
		 try (
		            Connection con = DataBaseUtil.establishConnection();
		            PreparedStatement ps = con.prepareStatement(check)
		        ) {
		            ps.setInt(1, readingId);

		            try (ResultSet rs = ps.executeQuery()) {
		                found= rs.next();   // true = exists
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		
		
		 		//validating given data
				if(!found) {
					System.out.println("Reading ID does NOT exist");
				}
				else {
					//getting the fields from the Air readings controller
					String[] fields=AirReadingsController.fields;
					Object[] obj = airDAO.findByReadingId(readingId);

					ArrayList<String[]> list = new ArrayList<>();

					String[] row = new String[obj.length];

					for (int i = 0; i < obj.length; i++) {
					    row[i] = String.valueOf(obj[i]);
					}

					list.add(row);
					//printing the data using printData method in PrintUtil
					PrintUtil.printData(fields, list);
				}
				
		
	}

	@Override
	public void getReadingsByUserId(String userId) {
		
		//extracting all the user Id from the database
		 ArrayList<String> userIdList = new ArrayList<>();
		try (
		        Connection con = DataBaseUtil.establishConnection();
	            PreparedStatement ps = con.prepareStatement("select user_id from air_readings");
	            ResultSet rs = ps.executeQuery();
		    ) {

          while (rs.next()) {
              userIdList.add(rs.getString("user_id"));
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
		String[] userIds = userIdList.toArray(new String[0]);
		
		//check if the given user id exists in table or not
		boolean found = false;
		for (String id : userIds) {
          if (id.equals(userId)) {   
              found = true;
              break;
       }}
		
				//validating given data
				if(userId==null) {
					System.out.println("User ID cannot be null");
				}
				else if(!found) {
					System.out.println("This User may not have given any Reading or may be not Exists");
				}
				else {
					//getting the fields from the Air readings controller
					String[] fields = AirReadingsController.fields;
					Object[] obj = airDAO.findByUserId(userId);

					ArrayList<String[]> list = new ArrayList<>();

					for (Object o : obj) {              
					    Object[] dataRow = (Object[]) o; 

					    String[] row = new String[dataRow.length];

					    for (int i = 0; i < dataRow.length; i++) {  
					        row[i] = String.valueOf(dataRow[i]);
					    }

					    list.add(row);
					}
					//printing the data using printData method in PrintUtil
					PrintUtil.printData(fields, list);

				}
				
		
	}

	@Override
	public void deleteByUserId(String userId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_readings WHERE user_id = ?";
		 try (
		            Connection con = DataBaseUtil.establishConnection();
		            PreparedStatement ps = con.prepareStatement(check)
		        ) {
		            ps.setString(1, userId);

		            try (ResultSet rs = ps.executeQuery()) {
		                found= rs.next();   // true = exists
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			
			
			
			if(!found) {
				System.out.println("User ID does NOT exist");
			}
			else {
				//deleting the user by method of DAO
				int rows=airDAO.deleteByUserId(userId);
				if(rows>0) {
					System.out.println("Reading deleted successfully");
				}
				else {
					System.out.println("Unable to delete Reading");
				}
			}
		
	}

}
