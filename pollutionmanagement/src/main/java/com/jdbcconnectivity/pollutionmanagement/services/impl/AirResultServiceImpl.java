package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.AirResultsController;
import com.jdbcconnectivity.pollutionmanagement.dao.AirResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.AirResultsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.AirResults;
import com.jdbcconnectivity.pollutionmanagement.services.AirResultService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

public class AirResultServiceImpl implements AirResultService{

	private AirResultsDAO airDAO;
	public static int resultID;
	public AirResultServiceImpl() {
		airDAO = new AirResultsDAOImpl();
	}
	
	
	@Override
	public void addResult(AirResults result) {
		if(result==null) {
			System.out.println("Result data is empty");
		}
		else {
			//calling the method to add data into the tables from DAO
			 resultID=airDAO.save(result);
			if(resultID>0) {
				System.out.println("Result saved successfully");
			}
			else {
				System.out.println("Unable to save Result");
			}
		}
		
	}

	@Override
	public void delete(int resultId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_results WHERE result_id = ?";
		 try (
		            Connection con = DataBaseUtil.establishConnection();
		            PreparedStatement ps = con.prepareStatement(check)
		        ) {
		            ps.setInt(1, resultId);

		            try (ResultSet rs = ps.executeQuery()) {
		                found= rs.next();   // true = exists
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			
			
			
			if(!found) {
				System.out.println("Result ID does NOT exist");
			}
			else {
				//calling the method to delete the result from DAO
				int rows=airDAO.delete(resultId);
				if(rows>0) {
					System.out.println("Result deleted successfully");
				}
				else {
					System.out.println("Unable to delete Result");
				}
			}
	}

	@Override
	public void getResult() {
		// To fetch records from DAO layer
		ArrayList<AirResults> resultList = airDAO.findAll();
		//changing reading object to string array
		ArrayList<String[]> dataList = new ArrayList<>();

		for (AirResults u : resultList) {
		    dataList.add(PrintUtil.toStringArray(u));
		}
		
		
		if(resultList.size() > 0)
		{
			//getting fields from the Air results controller
			String[] fields=AirResultsController.fields;
			//calling the method to print the data from PrintUtil
			PrintUtil.printData(fields, dataList);
		}
		else
		{
			System.out.println("No Result data found");
		}
		
	}

	@Override
	public void getResultByResultId(int resultId) {
		boolean found=false;
		//setting the SQL statement
		 String check ="SELECT 1 FROM air_results WHERE result_id = ?";
		 try (
		            Connection con = DataBaseUtil.establishConnection();
		            PreparedStatement ps = con.prepareStatement(check)
		        ) {
		            ps.setInt(1, resultId);

		            try (ResultSet rs = ps.executeQuery()) {
		                found= rs.next();   // true = exists
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		
		
		 		//validating given data
				if(!found) {
					System.out.println("Result ID does NOT exist");
				}
				else {
					//getting fields from the Air results controller
					String[] fields=AirResultsController.fields;
					Object[] obj = airDAO.findByResultId(resultId);

					ArrayList<String[]> list = new ArrayList<>();

					String[] row = new String[obj.length];

					for (int i = 0; i < obj.length; i++) {
					    row[i] = String.valueOf(obj[i]);
					}

					list.add(row);
					//calling the method to print the data from PrintUtil
					PrintUtil.printData(fields, list);
				}
				
		
		
	}



	@Override
	public void getResultByCategoryNameAndArea(String categoryName, String area) {
		
		//validating the data
		if(categoryName==null) {
			System.out.println("Category name cannot be null");
		}
		else if(area==null) {
			System.out.println("Area cannot be null");
		}
		else {
			//getting fields from the Air results controller
			String[] fields=AirResultsController.fields;
			Object[] obj = airDAO.findByCategoryNameAndArea(categoryName, area);
			if(obj==null) {
				System.out.println("No Matching Data Found");
				return;
			}
			
				ArrayList<String[]> list = new ArrayList<>();

				for (Object o : obj) {              
				    Object[] dataRow = (Object[]) o; 

				    String[] row = new String[dataRow.length];

				    for (int i = 0; i < dataRow.length; i++) {  
				        row[i] = String.valueOf(dataRow[i]);
				    }

				    list.add(row);
				}
				//calling the method to print the data from PrintUtil
				PrintUtil.printData(fields, list);
				}
		
	}


	@Override
	public void getResultByUserId(String userId) {
		
		//validating the data
		if(userId==null) {
			System.out.println("User ID cannot be null");
		}
		else {
			//getting fields from the Air results controller
			String[] fields = AirResultsController.fields;
			Object[] obj = airDAO.findByUserId(userId);
			if(obj==null) {
				System.out.println("No Matching Data Found");
				return;
			}

		ArrayList<String[]> list = new ArrayList<>();

		for (Object o : obj) {              
		    Object[] dataRow = (Object[]) o; 

		    String[] row = new String[dataRow.length];

		    for (int i = 0; i < dataRow.length; i++) {  
		        row[i] = String.valueOf(dataRow[i]);
		    }

		    list.add(row);
		}
		//calling the method to print the data from PrintUtil
		PrintUtil.printData(fields, list);
		}
		
	}
	

	@Override
	public void deleteByUserId(String userId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_results WHERE user_id = ?";
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
			
			
			//validating the data
			if(!found) {
				System.out.println("User ID does NOT exist");
			}
			else {
				//calling the method to delete the user by DAO
				int rows=airDAO.deleteByUserId(userId);
				if(rows>0) {
					System.out.println("Reading deleted successfully");
				}
				else {
					System.out.println("Unable to delete Reading");
				}
			}
		
	}


	@Override
	public void deleteByReadingId(int readingId) {
		boolean found=false;
		//setting SQL statement
		 String check ="SELECT 1 FROM air_results WHERE reading_id = ?";
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
			
			
			//validating the data
			if(!found) {
				System.out.println("Reading Id does NOT exist");
			}
			else {
				//calling the method to delete the reading from DAO
				int rows=airDAO.deleteByReadingId(readingId);
				if(rows>0) {
					System.out.println("Reading deleted successfully");
				}
				else {
					System.out.println("Unable to delete Reading");
				}
			}
		
	}



}
