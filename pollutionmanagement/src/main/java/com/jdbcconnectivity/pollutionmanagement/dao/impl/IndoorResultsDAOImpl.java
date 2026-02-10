package com.jdbcconnectivity.pollutionmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbcconnectivity.pollutionmanagement.dao.IndoorResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorResults;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class IndoorResultsDAOImpl implements IndoorResultsDAO{

	@Override
	public int save(IndoorResults indoorResult) {
		//setting the SQL query
		String indoor="insert into indoor_results(reading_id,category_name,user_id,value,status,area) values(?,?,?,?,?,?)";
		try(Connection con = DataBaseUtil.establishConnection())
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement(indoor,Statement.RETURN_GENERATED_KEYS);
			/*--- setting values into the query parameters */
			stmt.setInt(1, indoorResult.getReadingId());
			stmt.setString(2, indoorResult.getCategoryName());
			stmt.setString(3, indoorResult.getUserId());
			stmt.setDouble(4, indoorResult.getValue());
			stmt.setString(5, indoorResult.getStatus());
			stmt.setString(6, indoorResult.getArea());
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
	public ArrayList<IndoorResults> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<IndoorResults> resultList = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select result_id,"
					+"reading_id,"
					+"category_name,user_id,value,"
					+"status,area from indoor_results";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of result
			IndoorResults results;
			//fetching data
			while(result.next())
			{
				results = new IndoorResults();
				//setting result data
				results.setResultId(result.getInt(1));
				results.setReadingId(result.getInt(2));
				results.setCategoryName(result.getString(3));
				results.setUserId(result.getString(4));
				results.setValue(result.getInt(5));
				results.setStatus(result.getString(6));
				results.setArea(result.getString(7));
				//inserting data into arraylist
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
			PreparedStatement stmt = con.prepareStatement("delete from indoor_results where result_id = ? ");
			//setting query parameters
			stmt.setInt(1, resultId);
			
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
	public Object[] findByResultId(int resultId) {
		//setting the SQL query
		 String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM indoor_results WHERE result_id = ?";

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
		 String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM indoor_results WHERE category_name = ? and area = ?";

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
	//method to calculate the value out of PM2.5 value, co2 value and co value
	public double calculateValue(int pm2_5value, int co2Value, int coValue) {
		int pm=(pm2_5value/15)*100;
		int co2=(co2Value/1000)*100;
		int co=(coValue/9)*100;
		double value=pm+co2+co;
		return value;
	}

	@Override
	//method to return the status on the basis of value
	public String getStatus(double value) {
		if (value < 50) return "Good";
        else if (value >50 && value <101) return "Acceptable";
        else if (value >100 && value <151) return "Poor";
        else if (value >150 && value <201) return "Unhealthy";
        else return "Hazardous";
	}

	@Override
	public Object[] findByUserId(String userId) {
		//setting the SQL query
		 String sql = "SELECT result_id,reading_id,category_name,user_id,value,status,area FROM indoor_results WHERE user_id=?";

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
			PreparedStatement stmt = con.prepareStatement("delete from indoor_results where user_id = ? ");
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
			PreparedStatement stmt = con.prepareStatement("delete from indoor_results where reading_id = ? ");
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
