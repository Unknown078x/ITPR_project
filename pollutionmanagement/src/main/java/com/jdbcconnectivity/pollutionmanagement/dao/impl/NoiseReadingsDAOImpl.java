package com.jdbcconnectivity.pollutionmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbcconnectivity.pollutionmanagement.dao.NoiseReadingsDAO;
import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;
import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class NoiseReadingsDAOImpl implements NoiseReadingsDAO{

	@Override
	public int save(NoiseReadings noiseData) {
		//setting the SQL query
		String Noise="insert into noise_readings(user_id,sound_level,locality) values(?,?,?)";
		try(Connection con = DataBaseUtil.establishConnection();)
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement(Noise,Statement.RETURN_GENERATED_KEYS);
			/*--- setting values into the query parameters */
			stmt.setString(1, noiseData.getUserId());
			stmt.setInt(2, noiseData.getSoundLevel());
			stmt.setString(3, noiseData.getLocality());
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
	public ArrayList<NoiseReadings> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<NoiseReadings> readingList = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select reading_id,"
					+"user_id,sound_level,"
					+"locality from noise_readings";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of Reading
			NoiseReadings reading;
			//fetching data
			while(result.next())
			{
				reading = new NoiseReadings();
				//setting reading data
				reading.setReadingId(result.getInt(1));
				reading.setUserId(result.getString(2));
				reading.setSoundLevel(result.getInt(3));
				reading.setLocality(result.getString(4));
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
			PreparedStatement stmt = con.prepareStatement("delete from noise_readings where reading_id = ? ");
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
		//setting SQL statement
		   String sql = "SELECT reading_id, user_id,sound_level,locality FROM noise_readings WHERE reading_id = ?";

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
		//setting SQL statement
		  String sql = "SELECT reading_id, user_id,sound_level,locality FROM noise_readings WHERE user_id = ?";

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
			PreparedStatement stmt = con.prepareStatement("delete from noise_readings where user_id = ? ");
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
