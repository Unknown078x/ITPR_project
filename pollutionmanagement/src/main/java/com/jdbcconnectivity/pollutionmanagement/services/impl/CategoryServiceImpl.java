package com.jdbcconnectivity.pollutionmanagement.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jdbcconnectivity.pollutionmanagement.controller.CategoryController;
import com.jdbcconnectivity.pollutionmanagement.dao.CategoryDAO;
import com.jdbcconnectivity.pollutionmanagement.dao.impl.CategoryDAOImpl;
import com.jdbcconnectivity.pollutionmanagement.model.Category;
import com.jdbcconnectivity.pollutionmanagement.services.CategoryService;
import com.jdbcconnectivity.pollutionmanagement.util.DataBaseUtil;
import com.jdbcconnectivity.pollutionmanagement.util.PrintUtil;

/*
 * Service layer implementation for Category module
 * Handles business logic and validation
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO catDAO;
    public static int categoryID;

    public CategoryServiceImpl() {
        catDAO = new CategoryDAOImpl();
    }

    @Override
    public void registerCategory(Category category) {

        // basic validation
        if (category == null) {
            System.out.println("Category data is empty");
            return;
        }

        // save category using DAO
        categoryID = catDAO.save(category);

        if (categoryID > 0) {
            System.out.println("Category saved successfully");
        } else {
            System.out.println("Unable to save Category");
        }
    }

    @Override
    public void delete(int categoryId) {

        boolean found = false;

        // check whether category ID exists before deletion
        String check = "SELECT 1 FROM category WHERE category_id = ?";

        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement(check)
        ) {
            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                found = rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Category ID does NOT exist");
            return;
        }

        // delete category
        int rows = catDAO.delete(categoryId);

        if (rows > 0) {
            System.out.println("Category deleted successfully");
        } else {
            System.out.println("Unable to delete Category");
        }
    }

    @Override
    public void getCategoryList() {

        // fetch all categories
        ArrayList<Category> categoryList = catDAO.findAll();
        ArrayList<String[]> dataList = new ArrayList<>();

        // convert model objects to printable format
        for (Category c : categoryList) {
            dataList.add(PrintUtil.toStringArray(c));
        }

        if (categoryList.size() > 0) {
            PrintUtil.printData(CategoryController.fields, dataList);
        } else {
            System.out.println("No Category data found");
        }
    }

    @Override
    public void getCategoryDetails(int categoryId) {

        ArrayList<String> categoryList = new ArrayList<>();

        // fetch all category IDs to validate input
        try (
            Connection con = DataBaseUtil.establishConnection();
            PreparedStatement ps = con.prepareStatement("select category_id from category");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                categoryList.add(rs.getString("category_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check if given category ID exists
        boolean found = false;
        for (String id : categoryList) {
            if (id.equals(String.valueOf(categoryId))) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Category ID does NOT exist");
            return;
        }

        // fetch and display category details
        Object[] obj = catDAO.findByCategoryId(categoryId);
        ArrayList<String[]> list = new ArrayList<>();

        String[] row = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            row[i] = String.valueOf(obj[i]);
        }

        list.add(row);
        PrintUtil.printData(CategoryController.fields, list);
    }
}
