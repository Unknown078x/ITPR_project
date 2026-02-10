package com.jdbcconnectivity.pollutionmanagement.util;


import java.sql.*;

public class DataBaseUtil 
{
	
	
	
	//method to establish connection
	public static Connection establishConnection() 
	{
		Connection con = null; 
		try 
		{ 
			con = DriverManager.getConnection(System.getenv("DB_URL"),System.getenv("DB_USER"),System.getenv("DB_PASS"));
		}
		catch(SQLException sqe)
		{
			System.out.println(sqe); 
		}
		return con; 
	}

	
	//method to create tables if not exists
	public static void createTables()
	{
		try(Connection con = establishConnection();)
		{
			
			if(con != null)
			{
				//creating statement reference
				Statement stmt = con.createStatement();
				
				//Query for creation of users table
				String createUsersTable = "create table if not exists users("
						+"user_id varchar(30) primary key,"
						+"username varchar(50) not null unique,"
						+"password_hash varchar(255) not null,"
						+"full_name varchar(100) not null,"
						+"area varchar(100) not null,"
						+"role enum('admin', 'user') not null,"
						+"created_at timestamp default current_timestamp)";
				
				//Query for creation of category table
				String createCategoryTable = "create table if not exists category("
						+"category_id int primary key auto_increment,"
						+"category_name varchar(100) not null,"
						+"description varchar(400) not null)";
				
				//Query for creation of Air_readings table
				String createAirReadingsTable = "create table if not exists air_readings("
						+"reading_id int primary key auto_increment,"
						+"user_id varchar(30) not null references users(user_id),"
						+"pm2_5value double not null,"
						+"pm10value double not null,"
						+"locality varchar(100) not null,"
						+"reading_time timestamp default current_timestamp)";
				
				//Query for creation of Noise_readings table
				String createNoiseReadingsTable = "create table if not exists noise_readings("
						+"reading_id int primary key auto_increment,"
						+"user_id varchar(30) not null references users(user_id),"
						+"sound_level double not null,"
						+"locality varchar(100) not null,"
						+"reading_time timestamp default current_timestamp)";
				
				//Query for creation of Indoor_readings table
				String createIndoorReadingsTable = "create table if not exists indoor_readings("
						+"reading_id int primary key auto_increment,"
						+"user_id varchar(30) not null references users(user_id),"
						+"pm2_5value double not null,"
						+"co2_value double not null,"
						+"co_value int not null,"
						+"locality varchar(100) not null,"
						+"reading_time timestamp default current_timestamp)";
				
			
				
				//Query for creation of Air_results table
				String createAirResultsTable = "create table if not exists air_results("
						+"result_id int primary key auto_increment,"
						+"reading_id int not null references air_readings(reading_id),"
						+"category_name varchar(30) not null,"
						+"user_id varchar(30) not null references users(user_id),"
						+"value double not null,"
						+"status varchar(100) not null,"
						+"area varchar(100) not null,"
						+"calculated_on timestamp default current_timestamp)";
				
				//Query for creation of Noise_results table
				String createNoiseResultsTable = "create table if not exists noise_results("
						+"result_id int primary key auto_increment,"
						+"reading_id int not null references noise_readings(reading_id),"
						+"category_name varchar(100) not null,"
						+"user_id varchar(30) not null references users(user_id),"
						+"value double not null,"
						+"status varchar(100) not null,"
						+"area varchar(100) not null,"
						+"calculated_on timestamp default current_timestamp)";
				
				//Query for creation of Indoor_results table
				String createIndoorResultsTable = "create table if not exists indoor_results("
						+"result_id int primary key auto_increment,"
						+"reading_id int not null references air_readings(reading_id),"
						+"category_name varchar(100) not null,"
						+"user_id varchar(30) not null references users(user_id),"
						+"value double not null,"
						+"status varchar(100) not null,"
						+"area varchar(100) not null,"
						+"calculated_on timestamp default current_timestamp)";
				
			
			
				
				// adding queries into batches
				stmt.addBatch(createUsersTable);
				stmt.addBatch(createCategoryTable);
				stmt.addBatch(createAirReadingsTable);
				stmt.addBatch(createNoiseReadingsTable);
				stmt.addBatch(createIndoorReadingsTable);
				stmt.addBatch(createAirResultsTable);
				stmt.addBatch(createNoiseResultsTable);
				stmt.addBatch(createIndoorResultsTable);
				//executing batch
				stmt.executeBatch();
				System.out.println("Tables Created Successfully");
			}
		}catch(SQLException sqe)
		{
			System.out.println(sqe);
		}
		
	}
}


