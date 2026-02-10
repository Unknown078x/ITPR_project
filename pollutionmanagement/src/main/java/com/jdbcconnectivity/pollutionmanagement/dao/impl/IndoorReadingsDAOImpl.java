package com.jdbcconnectivity.pollutionmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbcconnectivity.pollutionmanagement.dao.IndoorReadingsDAO;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorReadings;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorReadings;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class IndoorReadingsDAOImpl implements IndoorReadingsDAO{

	@Override
	public int save(IndoorReadings indoorData) {
		//setting the SQL query
		String Air="insert into indoor_readings(user_id,pm2_5value,co2_value,co_value,locality) values(?,?,?,?,?)";
		try(Connection con = DataBaseUtil.establishConnection();)
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement(Air,Statement.RETURN_GENERATED_KEYS);
			/*--- setting values into the query parameters */
			stmt.setString(1, indoorData.getUserId());
			stmt.setInt(2, indoorData.getPm2_5value());
			stmt.setInt(3, indoorData.getCo2value());
			stmt.setInt(4, indoorData.getCoValue());
			stmt.setString(5, indoorData.getLocality());
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
	public ArrayList<IndoorReadings> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<IndoorReadings> readingList = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select reading_id,"
					+"user_id,pm2_5value,"
					+"co2_value,co_value,"
					+"locality from indoor_readings";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of Reading
			IndoorReadings reading;
			//fetching data
			while(result.next())
			{
				reading = new IndoorReadings();
				//setting reading data
				reading.setReadingId(result.getInt(1));
				reading.setUserId(result.getString(2));
				reading.setPm2_5value(result.getInt(3));
				reading.setCo2value(result.getInt(4));
				reading.setCoValue(result.getInt(5));
				reading.setLocality(result.getString(6));
				//inserting data into arraylist
				readingList.add(reading);
			}				
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		
		return readingList;
	}

	@Override
	public int delete(int readingId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from indoor_readings where reading_id = ? ");
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

	@Override
	public Object[] findByReadingId(int readingId) {
		//setting the SQL query
		  String sql = "SELECT reading_id, user_id,pm2_5value,co2_value,co_value,locality FROM indoor_readings WHERE reading_id = ?";

		    try (Connection con = DataBaseUtil.establishConnection()) 
		    {
		    	PreparedStatement stmt = con.prepareStatement(sql);
		    	stmt.setInt(1, readingId);
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
	public Object[] findByUserId(String userId) {
		//setting the SQL query
		  String sql = "SELECT reading_id, user_id,pm2_5value,co2_value,co_value,locality FROM indoor_readings WHERE user_id = ?";

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
			PreparedStatement stmt = con.prepareStatement("delete from indoor_readings where user_id = ? ");
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



}
