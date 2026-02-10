package com.jdbcconnectivity.pollutionmanagement.services;

import com.jdbcconnectivity.pollutionmanagement.model.Users;

public interface UsersService {
	
	//method to register a new user
	void registerUser(Users user);
	//method area of user using user id
	void updateArea(String userId,String area);
	//method to fetch all users
	void getUserList();
	//method to fetch user details using user id
	void getUserDetails(String userId);
	//method to delete a user
	void deleteUser(String userId);
	//method to update the password of user with user id
	void updatePassword(String userId,String password);
	//method to update password using old password
	void updateOldPassword(String userId,String oldPass,String newPass);
}
