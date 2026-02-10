package com.jdbcconnectivity.pollutionmanagement.model;
public class Users {
	
	//to store user id
	private String userId;
	//to store user name
	private String userName;
	//to store password
	private String password;
	//to store full name
	private String fullName;
	//to store area
	private String area;
	//to store role
	private String role;
	
	/*--------------------------------------------------------*/
	//Default constructor
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String userId, String userName, String password, String fullName, String area,
			String role) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
		this.area = area;
		this.role = role;
	}
	/*----------------------------------------------------*/
	/*----- Getter and setter ----------*/
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	/*------------------------------------------------*/
	
	@Override
	public String toString() {
		return "\n--------------------------------------------------------------------------------------\n"
				+ "User Id : " + userId 
				+ "\nUsername : " + userName 
				+ "\nUser's Full Name : " + fullName 
				+ "\nUser's Area : " + area
				+ "\nUser's Role : " + role
				+ "\n-----------------------------------------------------------------------------------\n";
	}
	
	
	
}
