package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.Category;

public interface CategoryDAO {

	
	/*---- to insert data into the table ----*/
	int save(Category categoryData);
	
	/*--- to fetch complete Category list ---*/
	ArrayList<Category> findAll();
	
	//method to delete the Category
		int delete(int categoryId);
	
	/*--- to fetch Category details based on Category id ----*/
	Object[] findByCategoryId(int categoryId);
	
}
