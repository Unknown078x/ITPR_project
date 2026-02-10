package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.NoiseReadingsController;
import com.jdbcconnectivity.pollutionmanagement.dao.NoiseReadingsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.NoiseReadingsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.NoiseReadings;
import com.jdbcconnectivity.pollutionmanagement.services.NoiseReadingsService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

/*
 * Service layer for Noise Readings
 * Handles validation and delegates DB operations to DAO
 */
public class NoiseReadingsServiceImpl implements NoiseReadingsService {

    private NoiseReadingsDAO noiseDAO;
    public static int readingID;

    public NoiseReadingsServiceImpl() {
        noiseDAO = new NoiseReadingsDAOImpl();
    }

    @Override
    public void takeReading(NoiseReadings reading) {

        // collect user IDs to validate foreign key (user_id)
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
        if (reading != null) {
            for (String id : userIdList) {
                if (id.equals(reading.getUserId())) {
                    found = true;
                    break;
                }
            }
        }

        // input validations
        if (reading == null) {
            System.out.println("Reading data is empty");
        }
        else if (reading.getSoundLevel() < 0) {
            System.out.println("Sound Level cannot be Negative");
        }
        else if (!found) {
            System.out.println("User ID doesn't exist");
        }
        else {
            // save reading using DAO
            readingID = noiseDAO.save(reading);

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
        String check = "SELECT 1 FROM noise_readings WHERE reading_id = ?";

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
        } else {
            int rows = noiseDAO.delete(readingId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }

    @Override
    public void getReadingList() {

        // fetch all noise readings
        ArrayList<NoiseReadings> readingList = noiseDAO.findAll();
        ArrayList<String[]> dataList = new ArrayList<>();

        // convert model objects to printable format
        for (NoiseReadings r : readingList) {
            dataList.add(PrintUtil.toStringArray(r));
        }

        if (readingList.size() > 0) {
            PrintUtil.printData(NoiseReadingsController.fields, dataList);
        } else {
            System.out.println("No Reading data found");
        }
    }

    @Override
    public void getReadingDetails(int readingId) {

        boolean found = false;

        // validate reading ID existence
        String check = "SELECT 1 FROM noise_readings WHERE reading_id = ?";

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
        } else {
            // fetch and display reading details
            Object[] obj = noiseDAO.findByReadingId(readingId);
            ArrayList<String[]> list = new ArrayList<>();

            String[] row = new String[obj.length];
            for (int i = 0; i < obj.length; i++) {
                row[i] = String.valueOf(obj[i]);
            }

            list.add(row);
            PrintUtil.printData(NoiseReadingsController.fields, list);
        }
    }

    @Override
    public void getReadingsByUserId(String userId) {

        // collect user IDs present in noise_readings table
        ArrayList<String> userIdList = new ArrayList<>();

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement("select user_id from noise_readings");
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
            // fetch and print readings for given user
            Object[] obj = noiseDAO.findByUserId(userId);
            ArrayList<String[]> list = new ArrayList<>();

            for (Object o : obj) {
                Object[] dataRow = (Object[]) o;
                String[] row = new String[dataRow.length];

                for (int i = 0; i < dataRow.length; i++) {
                    row[i] = String.valueOf(dataRow[i]);
                }
                list.add(row);
            }

            PrintUtil.printData(NoiseReadingsController.fields, list);
        }
    }

    @Override
    public void deleteByUserId(String userId) {

        boolean found = false;

        // check if readings exist for given user
        String check = "SELECT 1 FROM noise_readings WHERE user_id = ?";

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
        } else {
            int rows = noiseDAO.deleteByUserId(userId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }
}
