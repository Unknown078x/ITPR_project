package com.jdbcconnectivity.pollutionmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.dao.CategoryDAO;
import com.jdbcconnectivity.pollutionmanagement.model.Category;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class CategoryDAOImpl implements CategoryDAO{

	@Override
	public int save(Category categoryData) {
		//setting the SQL query
		String sql = "INSERT INTO category (category_name, description) VALUES (?, ?)";

	    try (
	        Connection con = DataBaseUtil.establishConnection();
	        PreparedStatement ps = con.prepareStatement(
	            sql, Statement.RETURN_GENERATED_KEYS
	        )
	    ) {
	        ps.setString(1, categoryData.getCategoryName());
	        ps.setString(2, categoryData.getDescription());

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Insert failed, no rows affected.");
	        }

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return -1;
	}

	@Override
	public ArrayList<Category> findAll() {
		//creating resultList of the ArrayList type
		ArrayList<Category> categoryData = new ArrayList<>();
		try(Connection con = DataBaseUtil.establishConnection())
		{
			//create statement
			Statement stmt = con.createStatement();
			//query to select data from table
			String sqlQuery = "Select category_id,"
					+"category_name,"
					+"description from category";
			//to execute the select query
			ResultSet result = stmt.executeQuery(sqlQuery);
			//Creating object of category
			Category category;
			//fetching data
			while(result.next())
			{
				category = new Category();
				category.setCategoryId(result.getInt(1));
				category.setCategoryName(result.getString(2));
				category.setDescription(result.getString(3));
				//adding data into ArrayList
				categoryData.add(category);
			}				
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		
		return categoryData;
	}

	@Override
	public int delete(int categoryId) {
		int deletedRows = 0;
		try(Connection con =DataBaseUtil.establishConnection())
		{
			//to create reference of preparedStatement interface
			PreparedStatement stmt = con.prepareStatement("delete from category where category_id = ? ");
			//setting query parameters
			stmt.setInt(1, categoryId);
			
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
	public Object[] findByCategoryId(int categoryId) {
			//setting the SQL query
		  String sql = "SELECT category_id, category_name, description FROM category WHERE category_id = ?";

		    try (Connection con = DataBaseUtil.establishConnection()) 
		    {
		    	PreparedStatement stmt = con.prepareStatement(sql);
		    	stmt.setInt(1, categoryId);
		    	ResultSet rs = stmt.executeQuery();

		        if (!rs.next()) {
		        	System.out.println("Category not found");
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

	

}
