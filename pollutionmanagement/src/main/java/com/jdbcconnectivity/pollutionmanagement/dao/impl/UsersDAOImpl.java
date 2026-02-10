package com.jdbcconnectivity.pollutionmanagement.dao.impl;
import at.favre.lib.crypto.bcrypt.*;

import java.sql.*;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.dao.UsersDAO;
import com.jdbcconnectivity.pollutionmanagement.model.Users;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class UsersDAOImpl implements UsersDAO{

	@Override
	public int save(Users userData) {
		
		int insertedRows=0;
		//changing the password into hash
		String bcryptHash = BCrypt.withDefaults()
		        .hashToString(12, userData.getPassword().toCharArray());
		//setting SQL statement
		String sql="insert into users(user_id,username,password_hash,full_name,area,role) values(?,?,?,?,?,?)";
		try(Connection con = DataBaseUtil.establishConnection();)
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement(sql);
			/*--- setting values into the query parameters */
			stmt.setString(1, userData.getUserId());
			stmt.setString(2, userData.getUserName());
			stmt.setString(3, bcryptHash);
			stmt.setString(4, userData.getFullName());
			stmt.setString(5, userData.getArea());
			stmt.setString(6, userData.getRole());
			/*-------------------------------------------------*/
			//executing the query
			insertedRows=stmt.executeUpdate();
			
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return insertedRows;
	}

	@Override
	public ArrayList<Users> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<Users> userList = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select user_id,"
					+"username,"
					+"full_name,area,"
					+"role from users";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of User
			Users user;
			//fetching data
			while(result.next())
			{
				user = new Users();
				//setting user data
				user.setUserId(result.getString(1));
				user.setUserName(result.getString(2));
				user.setFullName(result.getString(3));
				user.setArea(result.getString(4));
				user.setRole(result.getString(5));
				//inserting user into arraylist
				userList.add(user);
			}				
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		
		return userList;
	}

	@Override
	public int delete(String userId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from users where user_id = ? ");
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
	public Object[] findByUserId(String userId) {
		//setting SQL statement
	    String sql = "SELECT user_id, username,full_name,area,role FROM users WHERE user_id = ?";

	    try (Connection con = DataBaseUtil.establishConnection()) 
	    {
	    	PreparedStatement stmt = con.prepareStatement(sql);
	    	stmt.setString(1, userId);
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
	public int updateAreaByUserId(String userId, String area) {
		int updatedRows = 0;
		try(Connection con = DataBaseUtil.establishConnection();)
		{
			
			/*--- creating reference of Prepared Statement interface ---*/
			PreparedStatement stmt = con.prepareStatement("update users set area = ? where user_id = ?");
			/*--- setting values into the query parameters */
			stmt.setString(1, area);
			stmt.setString(2, userId);
			/*-------------------------------------------------*/
			//executing the query
			updatedRows = stmt.executeUpdate();
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		return updatedRows;
	}

	@Override
	public int updatePasswordByUserId(String userId, String password) {
		int updatedRows=0;
		
		//changing password into hash
	    String bcryptHash = BCrypt.withDefaults()
	            .hashToString(12, password.toCharArray());

	    String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

	    try (
	        Connection con = DataBaseUtil.establishConnection();
	    ) {
	    	PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, bcryptHash);
	        stmt.setString(2, userId);

	        updatedRows=stmt.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("Password update failed", e);
	    }
	    return updatedRows;
	}





	

}
