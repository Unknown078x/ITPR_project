package com.jdbcconnectivity.pollutionmanagement.model;

public class AirReadings {
	
	
	private int readingId;
	//to get user id
	private String userId;
	//to get PM 2.5 value
	private int pm2_5value;
	//to get PM 10 value
	private int pm10value;
	//tp get locality
	private String locality;
	
	/*--------------------------------------------------------*/
	//Default constructor
	public AirReadings() {
		super();
	}

	/*-------------------------------------------------------*/
	//constructor with parameters
	public AirReadings( String userId, int pm2_5value, int pm10value, String locality) {
		super();
		this.userId = userId;
		this.pm2_5value = pm2_5value;
		this.pm10value = pm10value;
		this.locality = locality;
	}
	
	/*----------------------------------------------------*/
	/*----- Getter and setter ----------*/

	

	public String getUserId() {
		return userId;
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

	public int getPm10value() {
		return pm10value;
	}

	public void setPm10value(int pm10value) {
		this.pm10value = pm10value;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public int getReadingId() {
		return readingId;
	}

	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}

	@Override
	public String toString() {
		
		return   "\nReading Id : " + readingId  
				+"\nUser Id : " + userId 
				+ "\nPM 2.5 Value : " + pm2_5value
				+ "\nPM 10 Value: " + pm10value 
				+ "\nLocality : " + locality
				+ "\n------------------------------------\n";
	}
	
	
	

}
