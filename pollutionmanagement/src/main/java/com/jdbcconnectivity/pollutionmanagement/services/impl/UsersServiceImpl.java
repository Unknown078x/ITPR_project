package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.*;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.UserController;
import com.jdbcconnectivity.pollutionmanagement.dao.UsersDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.UsersDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.Users;
import com.jdbcconnectivity.pollutionmanagement.services.UsersService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

import at.favre.lib.crypto.bcrypt.BCrypt;

/*
 * Service layer for Users module
 * Handles validation, password logic and delegates DB operations to DAO
 */
public class UsersServiceImpl implements UsersService {

    private UsersDAO userDAO;

    // constructor initializes DAO reference
    public UsersServiceImpl() {
        userDAO = new UsersDAOImpl();
    }

    @Override
    public void registerUser(Users user) {

        // basic input validations
        if (user == null) {
            System.out.println("User data is empty");
        }
        else if (user.getRole() == null ||
                (!"admin".equals(user.getRole()) && !"user".equals(user.getRole()))) {
            System.out.println("Invalid Role");
        }
        else if (user.getPassword().length() > 12) {
            System.out.println("Password Cannot be more than 12 characters in length");
        }
        else {
            // save user using DAO
            int rows = userDAO.save(user);

            if (rows > 0) {
                System.out.println("User Successfully Registered");
            } else {
                System.out.println("Unable to register the User");
            }
        }
    }

    @Override
    public void updateArea(String userId, String area) {

        // fetch all user IDs to validate existence
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

        // check if user ID exists
        boolean found = false;
        for (String id : userIdList) {
            if (id.equals(userId)) {
                found = true;
                break;
            }
        }

        // validations
        if (userId == null) {
            System.out.println("User ID cannot be null");
        }
        else if (area == null) {
            System.out.println("Area cannot be null");
        }
        else if (!found) {
            System.out.println("User ID does NOT exist");
        }
        else {
            int rows = userDAO.updateAreaByUserId(userId, area);

            if (rows > 0) {
                System.out.println("Area Sucessfully changed");
            } else {
                System.out.println("Unable to change the Area");
            }
        }
    }

    @Override
    public void getUserList() {

        // fetch all users
        ArrayList<Users> userList = userDAO.findAll();
        ArrayList<String[]> dataList = new ArrayList<>();

        // convert Users objects to printable format
        for (Users u : userList) {
            dataList.add(PrintUtil.toStringArrayUsers(u));
        }

        if (userList.size() > 0) {
            PrintUtil.printData(UserController.fields, dataList);
        } else {
            System.out.println("No User data found");
        }
    }

    @Override
    public void getUserDetails(String userId) {

        // fetch user IDs to validate input
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
            System.out.println("User ID does NOT exist");
        }
        else {
            // fetch and display user details
            Object[] obj = userDAO.findByUserId(userId);
            ArrayList<String[]> list = new ArrayList<>();

            String[] row = new String[obj.length];
            for (int i = 0; i < obj.length; i++) {
                row[i] = String.valueOf(obj[i]);
            }

            list.add(row);
            PrintUtil.printData(UserController.fields, list);
        }
    }

    @Override
    public void deleteUser(String userId) {

        // validate user existence before deletion
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
            System.out.println("User ID does NOT exist");
        }
        else {
            int rows = userDAO.delete(userId);

            if (rows > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("Unable to delete User");
            }
        }
    }

    @Override
    public void updatePassword(String userId, String password) {

        // validate user existence
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
        else if (password == null) {
            System.out.println("Password cannot be null");
        }
        else if (!found) {
            System.out.println("User ID does NOT exist");
        }
        else {
            int rows = userDAO.updatePasswordByUserId(userId, password);

            if (rows > 0) {
                System.out.println("Password Sucessfully changed");
            } else {
                System.out.println("Unable to change the Password");
            }
        }
    }

    @Override
    public void updateOldPassword(String userId, String oldPass, String newPass) {

        // fetch stored password hash for verification
        String selectSql = "SELECT password_hash FROM users WHERE user_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement selectStmt = con.prepareStatement(selectSql)
        ) {
            selectStmt.setString(1, userId);

            try (ResultSet rs = selectStmt.executeQuery()) {

                if (!rs.next()) {
                    System.out.print("User not Found");
                    return;
                }

                String storedHash = rs.getString("password_hash");

                // verify old password using BCrypt
                boolean matched = BCrypt.verifyer()
                        .verify(oldPass.toCharArray(), storedHash)
                        .verified;

                if (!matched) {
                    System.out.print("Wrong Old Password");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // final validations
        if (oldPass == null) {
            System.out.print("Old Password cannot be null");
        }
        else if (newPass == null) {
            System.out.print("New Password cannot be null");
        }
        else if (oldPass.equals(newPass)) {
            System.out.print("New password must be different");
        }
        else {
            int rows = userDAO.updatePasswordByUserId(userId, newPass);

            if (rows > 0) {
                System.out.print("Password changed successfully");
            } else {
                System.out.print("Unable to change Password");
            }
        }
    }
}
