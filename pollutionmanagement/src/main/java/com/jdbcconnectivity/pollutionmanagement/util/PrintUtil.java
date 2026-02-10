package com.jdbcconnectivity.pollutionmanagement.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.model.Users;


public class PrintUtil {

	
	public static boolean isFreshCall = true;
	//initializing a new arrayList of String array type
	public static ArrayList<String[]> datalist = new ArrayList<>();
	
	
	//method to print the border
		public static void printBorder(String fields[]) {
			for(int i=0;i<fields.length;i++) {
				int len=fields[i].length();
				String result = "-".repeat(len+2);
				System.out.print("+");
				System.out.print(result);
				if(i==fields.length-1) {
					System.out.println("+");
				}
			}
		}
		
		
		//method to print fields
		public static void printFields(String fields[]) {
			for(int i=0;i<fields.length;i++) {
				System.out.print("|");
				System.out.print(" "+fields[i]+" ");
				if(i==fields.length-1) {
					System.out.println("|");
				}
			}
		}
		
		
		//method to print the values 
		public static void printValues(String fields[],String values[]) {
			int arr[]=PrintUtil.findLength(fields);
			for(int i=0;i<values.length;i++) {
				int sp=arr[i]-values[i].length();
				String space=" ".repeat(sp);
				System.out.print("|");
				System.out.print(" "+values[i]+" "+space);
				if(i==values.length-1) {
					System.out.println("|");
				}
			}
		}
		
		
		//method for printing multiple values
		public static void printMultipleValues(String fields[], ArrayList<String[]> datalist) {
		    int arr[] = PrintUtil.findLength(fields);

		    for (String[] inner : datalist) {
		        for (int j = 0; j < inner.length; j++) {
		            int sp = arr[j] - inner[j].length();
		            String space = " ".repeat(Math.max(sp, 0));

		            System.out.print("|");
		            System.out.print(" " + inner[j] + " " + space);

		            if (j == inner.length - 1) {
		                System.out.println("|");
		            }
		        }
		    }
		    datalist.clear();
		    isFreshCall = true;
		}

		
		//method to find the length of all field's characters
		public static int[] findLength(String fields[]) {
			int arr[]=new int[fields.length];
			for(int i=0;i<fields.length;i++) {
				arr[i]=fields[i].length();		
			}
			return arr;
		}
		
		
		//method to convert object values into string array
		public static String[] convertObjectToString(Object[] arr) {
		    String[] result = new String[arr.length];

		    for (int i = 0; i < arr.length; i++) {
		        result[i] = String.valueOf(arr[i]);
		    }

		    return result;
		}
	
		
		public static void getData(Object arr[]) {
		    if (isFreshCall) {
		        datalist.clear();
		        isFreshCall = false;
		    }

		    String data[] = PrintUtil.convertObjectToString(arr);
		    datalist.add(data);
		}

		//change object into String array
		public static String[] toStringArray(Object obj) {
		        Field[] fields = obj.getClass().getDeclaredFields();
		        String[] row = new String[fields.length];

		        try {
		            for (int i = 0; i < fields.length; i++) {
		                fields[i].setAccessible(true); // private fields access
		                Object value = fields[i].get(obj);
		                row[i] = (value == null) ? "" : value.toString();
		            }
		        } catch (IllegalAccessException e) {
		            throw new RuntimeException("Conversion failed", e);
		        }

		        return row;
		 }
		   
		//method for converting Users into string array
		   public static String[] toStringArrayUsers(Users u) {
			    return new String[] {
			        u.getUserId() == null ? "" : u.getUserId(),
			        u.getUserName() == null ? "" : u.getUserName(),
			        u.getFullName() == null ? "" : u.getFullName(),
			        u.getArea() == null ? "" : u.getArea(),
			        u.getRole() == null ? "" : u.getRole()
			    };
			}


		
		
		
		//method to print all the data with border and fields
		public static void printData(String[] fields,ArrayList<String[]> datalist) {
			
			ArrayList<String[]> tempData = new ArrayList<>(datalist);

			  
			datalist.clear();
			//calling method to print border
			PrintUtil.printBorder(fields);
			//calling method to print fields
			PrintUtil.printFields(fields);
			//calling method to print border
			PrintUtil.printBorder(fields);
			//calling method to print multiple values
			PrintUtil.printMultipleValues(fields,tempData);
			//calling method to print border
			PrintUtil.printBorder(fields);
	
		}
		
		
		
}
