package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoJDBCTest {
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();

    private ProductCategory productCategory = new ProductCategory("Ebook", "Portable", "blablabla");


    @BeforeEach
    private void clear() {
        String query = "TRUNCATE TABLE categories, suppliers, products RESTART IDENTITY";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ProductCategoryDaoJDBC preparedBase() {
        productCategoryDaoJDBC.add(productCategory);
        return productCategoryDaoJDBC;
    }

    @Test
    @DisplayName("Adding to database")
    void addTest() {
        productCategoryDaoJDBC = preparedBase();
        assertEquals(1, productCategoryDaoJDBC.getAll().size());
    }


    @Test
    @DisplayName("Find by id")
    public void findByIdTest() {
        productCategoryDaoJDBC = preparedBase();
        int idProductCategory = 1;
        ProductCategory productCategoryFromDatabase = productCategoryDaoJDBC.find(idProductCategory);
        assertEquals(idProductCategory, productCategoryFromDatabase.getId());
    }

    @Test
    @DisplayName("Find by id(not exists)")
    public void findByNotExistsIdTest() {
        productCategoryDaoJDBC = preparedBase();
        int idProductCategory = 999;
        assertNull(productCategoryDaoJDBC.find(idProductCategory));
    }

    @Test
    @DisplayName("Find by name")
    void findByNameTest() {
        productCategoryDaoJDBC = preparedBase();
        String nameProductCategory = productCategory.getName();
        ProductCategory productCategory1 = productCategoryDaoJDBC.findByName(nameProductCategory);
        assertEquals(productCategory.getName(), productCategory1.getName() );
    }

    @Test
    @DisplayName("Find by name(not exists)")
    void findByNotExistsNameTest() {
        productCategoryDaoJDBC = preparedBase();
        String nameproductCategory = "qwerty";
        assertNull(productCategoryDaoJDBC.findByName(nameproductCategory));
    }

    @Test
    @DisplayName("Delete by id from database")
    public void removeTest(){
        productCategoryDaoJDBC = preparedBase();
        int idProductCategory = 1;
        productCategoryDaoJDBC.remove(idProductCategory);
        assertEquals(0, productCategoryDaoJDBC.getAll().size());
    }

    @Test
    @DisplayName("Get all records database")
    public void getAllTest() {
        productCategoryDaoJDBC = preparedBase();
        ProductCategory productCategory1 = new ProductCategory("Laptop", "Hardware", "A personal computer, you need it to code");
        productCategoryDaoJDBC.add(productCategory1);
        assertEquals(2, productCategoryDaoJDBC.getAll().size());
    }
}