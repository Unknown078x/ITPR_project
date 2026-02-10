package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.IndoorResultsController;
import com.jdbcconnectivity.pollutionmanagement.dao.IndoorResultsDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.IndoorResultsDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.IndoorResults;
import com.jdbcconnectivity.pollutionmanagement.services.IndoorResultService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

/*
 * Service layer for Indoor Results
 * Contains validation + delegates DB operations to DAO
 */
public class IndoorResultServiceImpl implements IndoorResultService {

    private IndoorResultsDAO indoorDAO;
    public static int resultID;

    public IndoorResultServiceImpl() {
        indoorDAO = new IndoorResultsDAOImpl();
    }

    @Override
    public void addResult(IndoorResults result) {

        // basic null validation
        if (result == null) {
            System.out.println("Result data is empty");
            return;
        }

        // save result using DAO
        resultID = indoorDAO.save(result);

        if (resultID > 0) {
            System.out.println("Result saved successfully");
        } else {
            System.out.println("Unable to save Result");
        }
    }

    @Override
    public void delete(int resultId) {

        boolean found = false;

        // check if result ID exists before deletion
        String check = "SELECT 1 FROM indoor_results WHERE result_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setInt(1, resultId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Result ID does NOT exist");
        } else {
            int rows = indoorDAO.delete(resultId);

            if (rows > 0) {
                System.out.println("Result deleted successfully");
            } else {
                System.out.println("Unable to delete Result");
            }
        }
    }

    @Override
    public void getResult() {

        // fetch all indoor results
        ArrayList<IndoorResults> resultList = indoorDAO.findAll();
        ArrayList<String[]> dataList = new ArrayList<>();

        // convert model objects to printable rows
        for (IndoorResults r : resultList) {
            dataList.add(PrintUtil.toStringArray(r));
        }

        if (resultList.size() > 0) {
            PrintUtil.printData(IndoorResultsController.fields, dataList);
        } else {
            System.out.println("No Result data found");
        }
    }

    @Override
    public void getResultByResultId(int resultId) {

        boolean found = false;

        // validate result ID existence
        String check = "SELECT 1 FROM indoor_results WHERE result_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setInt(1, resultId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Result ID does NOT exist");
        } else {
            // fetch and display specific result
            Object[] obj = indoorDAO.findByResultId(resultId);
            ArrayList<String[]> list = new ArrayList<>();

            String[] row = new String[obj.length];
            for (int i = 0; i < obj.length; i++) {
                row[i] = String.valueOf(obj[i]);
            }

            list.add(row);
            PrintUtil.printData(IndoorResultsController.fields, list);
        }
    }

    @Override
    public void getResultByCategoryNameAndArea(String categoryName, String area) {

        // input validation
        if (categoryName == null) {
            System.out.println("Category name cannot be null");
            return;
        }
        if (area == null) {
            System.out.println("Area cannot be null");
            return;
        }

        // fetch results based on category + area
        Object[] obj = indoorDAO.findByCategoryNameAndArea(categoryName, area);

        if (obj == null) {
            System.out.println("No Matching Data Found");
            return;
        }

        ArrayList<String[]> list = new ArrayList<>();

        for (Object o : obj) {
            Object[] dataRow = (Object[]) o;
            String[] row = new String[dataRow.length];

            for (int i = 0; i < dataRow.length; i++) {
                row[i] = String.valueOf(dataRow[i]);
            }
            list.add(row);
        }

        PrintUtil.printData(IndoorResultsController.fields, list);
    }

    @Override
    public void getResultByUserId(String userId) {

        // validate user ID
        if (userId == null) {
            System.out.println("User ID cannot be null");
            return;
        }

        Object[] obj = indoorDAO.findByUserId(userId);

        if (obj == null) {
            System.out.println("No Matching Data Found");
            return;
        }

        ArrayList<String[]> list = new ArrayList<>();

        for (Object o : obj) {
            Object[] dataRow = (Object[]) o;
            String[] row = new String[dataRow.length];

            for (int i = 0; i < dataRow.length; i++) {
                row[i] = String.valueOf(dataRow[i]);
            }
            list.add(row);
        }

        PrintUtil.printData(IndoorResultsController.fields, list);
    }

    @Override
    public void deleteByUserId(String userId) {

        boolean found = false;

        // check if any result exists for given user
        String check = "SELECT 1 FROM indoor_results WHERE user_id = ?";

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
            int rows = indoorDAO.deleteByUserId(userId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }

    @Override
    public void deleteByReadingId(int readingId) {

        boolean found = false;

        // check if result exists for given reading ID
        String check = "SELECT 1 FROM indoor_results WHERE reading_id = ?";

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
            System.out.println("Reading Id does NOT exist");
        } else {
            int rows = indoorDAO.deleteByReadingId(readingId);

            if (rows > 0) {
                System.out.println("Reading deleted successfully");
            } else {
                System.out.println("Unable to delete Reading");
            }
        }
    }
}
