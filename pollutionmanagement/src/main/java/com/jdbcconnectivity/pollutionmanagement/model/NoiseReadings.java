package com.jdbcconnectivity.pollutionmanagement.model;

public class NoiseReadings {
	
		private int readingId;
		//to get user id
		private String userId;
		//to get sound level value
		private int soundLevel;
		//tp get locality
		private String locality;
		
		/*--------------------------------------------------------*/
		//Default constructor
		public NoiseReadings() {
			super();
			// TODO Auto-generated constructor stub
		}

		/*-------------------------------------------------------*/
		//constructor with parameters

		public NoiseReadings( String userId, int soundLevel, String locality) {
			super();
			this.userId = userId;
			this.soundLevel = soundLevel;
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


		public int getSoundLevel() {
			return soundLevel;
		}


		public void setSoundLevel(int soundLevel) {
			this.soundLevel = soundLevel;
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
					+ "\nSound Level : " + soundLevel
					+ "\nLocality : " + locality
					+ "\n-----------------------------------------------------------------------------------\n";
		}
		
		
		
		
		
}
