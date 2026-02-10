package com.jdbcconnectivity.pollutionmanagement.model;

public class IndoorReadings {

	private int readingId;
	//to get user id
	private String userId;
	//to get PM 2.5 value
	private int pm2_5value;
	//to get co2 value
	private int co2value;
	//to get CO value
	private int coValue;
	//tp get locality
	private String locality;
	
	
	/*--------------------------------------------------------*/
	//Default constructor
	public IndoorReadings() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/*-------------------------------------------------------*/
	//constructor with parameters
	public IndoorReadings( String userId, int pm2_5value, int co2value, int coValue, String locality) {
		super();
		this.userId = userId;
		this.pm2_5value = pm2_5value;
		this.co2value = co2value;
		this.coValue = coValue;
		this.locality = locality;
	}
	
	/*----------------------------------------------------*/
	/*----- Getter and setter ----------*/




	public String getUserId() {
		return userId;
	}

	public int getReadingId() {
		return readingId;
	}

	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPm2_5value() {
		return pm2_5value;
	}

	public void setPm2_5value(int pm2_5value) {
		this.pm2_5value = pm2_5value;
	}

	public int getCo2value() {
		return co2value;
	}

	public void setCo2value(int co2value) {
		this.co2value = co2value;
	}

	public int getCoValue() {
		return coValue;
	}

	public void setCoValue(int coValue) {
		this.coValue = coValue;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	@Override
	public String toString() {
		return "\n--------------------------------------------------------------------------------------\n"
				+ "\nReading Id : " + readingId 
				+ "\nUser Id : " + userId
				+ "\nPM 2.5 value : " + pm2_5value
				+ "\nCO2 value : " + co2value
				+ "\nCO value : " + coValue
				+ "\nLocality : " + locality
				+ "\n-----------------------------------------------------------------------------------\n";
	}
	
	
	
	
	
			
}
