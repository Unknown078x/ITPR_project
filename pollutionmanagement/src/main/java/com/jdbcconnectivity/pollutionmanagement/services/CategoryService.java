package com.jdbcconnectivity.pollutionmanagement.services;

import com.jdbcconnectivity.pollutionmanagement.model.Category;

public interface CategoryService {

	//method to register a new category
	void registerCategory(Category category);
	//method to delete category
	void delete(int categoryId);
	//method to fetch all Categories
	void getCategoryList();
	//method to fetch category details of a particular category
	void getCategoryDetails(int categoryId);
}
