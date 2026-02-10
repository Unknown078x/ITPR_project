package com.jdbcconnectivity.pollutionmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbcconnectivity.pollutionmanagement.dao.AirResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.model.AirResults;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class AirResultsDAOImpl implements AirResultsDAO{

	@Override
	public int save(AirResults airResult) {
	
		//setting the SQL query
		String air="insert into air_results(reading_id,category_name,user_id,value,status,area) values(?,?,?,?,?,?)";
		try(Connection con = DataBaseUtil.establishConnection())
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement(air,Statement.RETURN_GENERATED_KEYS);
			/*--- setting values into the query parameters */
			stmt.setInt(1, airResult.getReadingId());
			stmt.setString(2, airResult.getCategoryName());
			stmt.setString(3, airResult.getUserId());
			stmt.setDouble(4, airResult.getValue());
			stmt.setString(5,airResult.getStatus());
			stmt.setString(6, airResult.getArea());
			/*-------------------------------------------------*/
			//executing the query
			stmt.executeUpdate();
			 ResultSet rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getInt(1); // reading_id
	            }
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return -1;
	}
	@Override
	public ArrayList<AirResults> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<AirResults> resultList = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select result_id,"
					+"reading_id,"
					+"category_name,user_id,value,"
					+"status,area from air_results";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of result
			AirResults results;
			//fetching data
			while(result.next())
			{
				results = new AirResults();
				//setting result data
				results.setResultId(result.getInt(1));
				results.setReadingId(result.getInt(2));
				results.setCategoryName(result.getString(3));
				results.setUserId(result.getString(4));
				results.setValue(result.getInt(5));
				results.setStatus(result.getString(6));
				results.setArea(result.getString(7));
				//inserting data into arrayList
				resultList.add(results);
			}				
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		
		return resultList;
	}

	@Override
	public int delete(int resultId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from air_results where result_id = ? ");
			//setting query parameters
			stmt.setInt(1, resultId);
			
			//to execute the query
			deletedRows = stmt.executeUpdate();
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return deletedRows;
	}

	@Override
	public Object[] findByResultId(int resultId) {
		  String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM air_results WHERE result_id = ?";

		    try (Connection con = DataBaseUtil.establishConnection()) 
		    {
		    	PreparedStatement stmt = con.prepareStatement(sql);
		    	stmt.setInt(1, resultId);
		    	ResultSet rs = stmt.executeQuery();

		        if (!rs.next()) {
		            return null; // or new Object[0]
		        }

		        ResultSetMetaData meta = rs.getMetaData();
		        int columnCount = meta.getColumnCount();

		        Object[] data = new Object[columnCount];

		        for (int i = 1; i <= columnCount; i++) {
		            data[i - 1] = rs.getObject(i);
		        }

		        return data;

		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
	}

	
	@Override
	public Object[] findByCategoryNameAndArea(String categoryName, String area) {
		//setting the SQL query
		 String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM air_results WHERE category_name = ? and area = ?";

		 try (Connection con = DataBaseUtil.establishConnection()) {

			    PreparedStatement stmt = con.prepareStatement(sql);
			    stmt.setString(1, categoryName);
			    stmt.setString(2, area);
			    ResultSet rs = stmt.executeQuery();

			    ResultSetMetaData meta = rs.getMetaData();
			    int columnCount = meta.getColumnCount();

			    List<Object[]> tempList = new ArrayList<>();

			    while (rs.next()) {
			        Object[] row = new Object[columnCount];
			        for (int i = 1; i <= columnCount; i++) {
			            row[i - 1] = rs.getObject(i);
			        }
			        tempList.add(row);
			    }

			    if (tempList.isEmpty()) {
			        return null;
			    }

			    return tempList.toArray(new Object[0]);
			    
			} catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
	}

	@Override
	//method to return the maximum value
	public double calculateValue(double pm2_5value, double pm10value) {
		if(pm2_5value>pm10value) return pm2_5value;
		else if(pm2_5value<pm10value) return pm10value;
		else return pm10value;
	}
	
	
	@Override
	//method to return the status for PM2.5 value
	public String getStatus25(double value) {
		if (value < 30) return "Good";
        else if (value >30 && value <61) return "Satisfactory";
        else if (value >60 && value <91) return "Moderate";
        else if (value >90 && value <121) return "Poor";
        else if (value >120 && value <251) return "Very Poor";
        else return "Hazardous";
	}
	
	@Override
	//method to return the status for PM10 value
	public String getStatus10(double value) {
		if (value < 50) return "Good";
        else if (value >50 && value <101) return "Satisfactory";
        else if (value >100 && value <251) return "Moderate";
        else if (value >250 && value <351) return "Poor";
        else if (value >350 && value <431) return "Very Poor";
        else return "Hazardous";
	}
	@Override
	public Object[] findByUserId(String userId) {
		 //setting the SQL query
		 String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM air_results WHERE user_id=?";

		 try (Connection con = DataBaseUtil.establishConnection()) {

			    PreparedStatement stmt = con.prepareStatement(sql);
			    stmt.setString(1, userId);
			    ResultSet rs = stmt.executeQuery();

			    ResultSetMetaData meta = rs.getMetaData();
			    int columnCount = meta.getColumnCount();

			    List<Object[]> tempList = new ArrayList<>();

			    while (rs.next()) {
			        Object[] row = new Object[columnCount];
			        for (int i = 1; i <= columnCount; i++) {
			            row[i - 1] = rs.getObject(i);
			        }
			        tempList.add(row);
			    }

			    if (tempList.isEmpty()) {
			        return null;
			    }

			  
			    return tempList.toArray(new Object[0]);
			} catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
	}
	@Override
	public int deleteByUserId(String userId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from air_results where user_id = ? ");
			//setting query parameters
			stmt.setString(1, userId);
			
			//to exceute the query
			deletedRows = stmt.executeUpdate();
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return deletedRows;
	}
	@Override
	public int deleteByReadingId(int readingId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from air_results where reading_id = ? ");
			//setting query parameters
			stmt.setInt(1, readingId);
			
			//to exceute the query
			deletedRows = stmt.executeUpdate();
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return deletedRows;
	}

	
	
	

}
