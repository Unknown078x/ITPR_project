package com.jdbcconnectivity.pollutionmanagement.dao;

import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.Users;


public interface UsersDAO {
	/*---- to insert data into the table ----*/
	int save(Users userData);
	
	/*--- to fetch complete users list ---*/
	ArrayList<Users> findAll();
	
	//method to delete the user
	int delete(String userId);
	
	/*--- to fetch user details based on user id ----*/
	Object[] findByUserId(String userId);
	
	/*---- to update area based on user id ---*/
	int updateAreaByUserId(String userId, String area);

	/*---update password using user id---*/
	int updatePasswordByUserId(String userId,String password);
	
	
}
