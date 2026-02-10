package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.IndoorReadingsController;
import com.jdbcconnectivity.pollutionmanagement.dao.IndoorReadingsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.IndoorReadingsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorReadings;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorReadingsService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

/*
 * Service layer for Indoor Readings
 * Handles validation and coordination between controller and DAO
 */
public class IndoorReadingsServiceImpl implements IndoorReadingsService {

    private IndoorReadingsDAO indoorDAO;
    public static int readingID;

    public IndoorReadingsServiceImpl() {
        indoorDAO = new IndoorReadingsDAOImpl();
    }

    @Override
    public void takeReading(IndoorReadings reading) {

        // collect all user IDs to validate foreign key (user_id)
        ArrayList<String> userIdList = new ArrayList<>();

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement("select user_id from users");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                userIdList.add(rs.getString("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if given user ID exists
        boolean found = false;
        for (String id : userIdList) {
            if (reading != null && id.equals(reading.getUserId())) {
                found = true;
                break;
            }
        }

        // input validations
        if (reading == null) {
            System.out.println("Reading data is empty");
        }
        else if (reading.getPm2_5value() < 0) {
            System.out.println("PM 2.5 value cannot be Negative");
        }
        else if (reading.getCo2value() < 0) {
            System.out.println("CO 2 value cannot be Negative");
        }
        else if (reading.getCoValue() < 0) {
            System.out.println("CO value cannot be Negative");
        }
        else if (!found) {
            System.out.println("User ID doesn't exist");
        }
        else {
            // save reading using DAO
            readingID = indoorDAO.save(reading);

            if (readingID > 0) {
                System.out.println("Reading saved successfully");
            } else {
                System.out.println("Unable to save Reading");
            }
        }
    }

    @Override
    public void delete(int readingId) {

        boolean found = false;

        // check if reading ID exists before deletion
        String check = "SELECT 1 FROM indoor_readings WHERE reading_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setInt(1, readingId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Reading ID does NOT exist");
        }
        else {
            int rows = indoorDAO.delete(readingId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }

    @Override
    public void getReadingList() {

        // fetch all indoor readings
        ArrayList<IndoorReadings> readingList = indoorDAO.findAll();
        ArrayList<String[]> dataList = new ArrayList<>();

        // convert model objects for printing
        for (IndoorReadings r : readingList) {
            dataList.add(PrintUtil.toStringArray(r));
        }

        if (readingList.size() > 0) {
            PrintUtil.printData(IndoorReadingsController.fields, dataList);
        } else {
            System.out.println("No Reading data found");
        }
    }

    @Override
    public void getReadingDetails(int readingId) {

        boolean found = false;

        // validate reading ID existence
        String check = "SELECT 1 FROM air_readings WHERE reading_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setInt(1, readingId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Reading ID does NOT exist");
        }
        else {
            // fetch and print specific reading details
            Object[] obj = indoorDAO.findByReadingId(readingId);
            ArrayList<String[]> list = new ArrayList<>();

            String[] row = new String[obj.length];
            for (int i = 0; i < obj.length; i++) {
                row[i] = String.valueOf(obj[i]);
            }

            list.add(row);
            PrintUtil.printData(IndoorReadingsController.fields, list);
        }
    }

    @Override
    public void getReadingsByUserId(String userId) {

        // collect user IDs present in indoor_readings table
        ArrayList<String> userIdList = new ArrayList<>();

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement("select user_id from indoor_readings");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                userIdList.add(rs.getString("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if user has any readings
        boolean found = false;
        for (String id : userIdList) {
            if (id.equals(userId)) {
                found = true;
                break;
            }
        }

        if (userId == null) {
            System.out.println("User ID cannot be null");
        }
        else if (!found) {
            System.out.println("This User may not have given any Reading or may not exist");
        }
        else {
            // fetch and print all readings for a user
            Object[] obj = indoorDAO.findByUserId(userId);
            ArrayList<String[]> list = new ArrayList<>();

            for (Object o : obj) {
                Object[] dataRow = (Object[]) o;
                String[] row = new String[dataRow.length];

                for (int i = 0; i < dataRow.length; i++) {
                    row[i] = String.valueOf(dataRow[i]);
                }
                list.add(row);
            }

            PrintUtil.printData(IndoorReadingsController.fields, list);
        }
    }

    @Override
    public void deleteByUserId(String userId) {

        boolean found = false;

        // check if readings exist for given user
        String check = "SELECT 1 FROM indoor_readings WHERE user_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("User ID does NOT exist");
        }
        else {
            int rows = indoorDAO.deleteByUserId(userId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }
}
