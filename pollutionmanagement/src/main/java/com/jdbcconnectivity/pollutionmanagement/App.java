package com.jdbcconnectivity.pollutionmanagement;

import com.jdbcconnectivity.pollutionmanagement.util.AppInterface;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;

public class App {

	public static void main(String[] args) {
		//calling method to create tables
//		DataBaseUtil.createTables();
		//calling method for the app interface
		AppInterface.run();
	}
	

}
