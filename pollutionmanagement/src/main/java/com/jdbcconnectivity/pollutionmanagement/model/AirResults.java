package com.jdbcconnectivity.pollutionmanagement.model;

public class AirResults {
	
	private int resultId;
	//to get reading id
	private int readingId;
	//to get category id
	private String categoryName;
	//to get user id
	private String userId;
	//to get status
	private String status;
	//to get value
	private double value;
	//to get area
	private String area;

	/*--------------------------------------------------------*/
	//Default constructor
	public AirResults() {
		super();
		// TODO Auto-generated constructor stub
	}

	//--------------------------------------------------------------------
	//constructor with parameters
	public AirResults( int readingId, String categoryName,String userId, double value,String status,String area) {
		super();
		this.readingId = readingId;
		this.categoryName= categoryName;
		this.userId=userId;
		this.value = value;
		this.status=status;
		this.area=area;
	}
	
	//----------------------------------------------------------------------
	//getter and setters

	

	public String getArea() {
		return area;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getReadingId() {
		return readingId;
	}

	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "\n--------------------------------------------------------------------------------------\n"
				+ "\nResult Id : " + resultId
				+ "\nReading Id : " + readingId
				+ "\nCategory Name : " + categoryName
				+ "Result Id : " + userId 
				+ "\nValue : " + value
				+ "\nStatus : " + status
				+ "\nArea : " + area
				+ "\n-----------------------------------------------------------------------------------\n";
	}
	

	
	
	

}
