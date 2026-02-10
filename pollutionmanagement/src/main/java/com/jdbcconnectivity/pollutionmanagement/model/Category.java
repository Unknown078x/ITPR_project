package com.jdbcconnectivity.pollutionmanagement.model;

public class Category {
	
	private int categoryId;
	//to store category name
	private String categoryName;
	//to store description
	private String description;

	
	/*--------------------------------------------------------*/
	//Default constructor
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category( String categoryName, String description) {
		super();
		this.categoryName = categoryName;
		this.description = description;
	}
	/*----------------------------------------------------*/
	/*----- Getter and setter ----------*/
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	/*------------------------------------------------*/
	@Override
	public String toString() {
		return    "\n--------------------------------------------------------------------------------------\n"
				+ "\nCategory Id : " + categoryId 
				+ "\nCategory name : " + categoryName 
				+ "\nDescription : " + description
				+ "\n--------------------------------------------------------------------------------------\n";
	}
	


}
