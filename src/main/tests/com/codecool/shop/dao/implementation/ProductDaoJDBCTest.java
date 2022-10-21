package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class ProductDaoJDBCTest {
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();    ;
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    private ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance();

    private Supplier supplier = new Supplier("Lenovo", "Computers");
    private ProductCategory productCategory = new ProductCategory("Ebook", "Portable", "blablabla");
    private Product product = new Product("Kindle Paperwhite", 50, "USD", "We love Kindle :)", productCategory, supplier);


    @BeforeEach
    private void clear() {
        String query = "TRUNCATE TABLE suppliers, categories, products RESTART IDENTITY";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ProductDaoJDBC preparedBase() {
        supplierDaoJDBC.add(supplier);
        productCategoryDaoJDBC.add(productCategory);
        productDaoJDBC.add(product);
        return productDaoJDBC;
    }

    @Test
    @DisplayName("Adding to database")
    public void addTest() {
        productDaoJDBC = preparedBase();
//        assertEquals(0, productDaoJDBC.getAll().size());
        productDaoJDBC.add(product);
        assertEquals(2, productDaoJDBC.getAll().size());
    }

    @Test
    @DisplayName("Find by id")
    public void findByIdTest() {
        productDaoJDBC = preparedBase();
        int idProduct = 1;
        Product product = new Product("Kindle Paperwhite",
                50, "USD",
                "We love Kindle :)",
                productCategory,
                supplier);
        productDaoJDBC.add(product);

        Product productFromDatabase = productDaoJDBC.find(idProduct);
        assertEquals(product.getName(), productFromDatabase.getName());
        assertEquals(product.getDefaultPrice(), productFromDatabase.getDefaultPrice());
    }

    @Test
    @DisplayName("Find by id(not exists)")
    public void findByNotExistsIdTest() {
        productDaoJDBC = preparedBase();
        int idProduct = 999;
        assertNull(productDaoJDBC.find(idProduct));
    }

    @Test
    @DisplayName("Get all records database")
    public void getAllTest() {
        productDaoJDBC = preparedBase();
        Product product1 = new Product("Amazon Fire", 49, "USD", "Fantastic price.", productCategory, supplier);
        productDaoJDBC.add(product1);
        assertEquals(2, productDaoJDBC.getAll().size());
    }

    @Test
    @DisplayName("Get by supplier")
    public void getBySupplierTest() {
        productDaoJDBC = preparedBase();
        assertEquals(1,productDaoJDBC.getBy(supplier).size());
    }

    @Test
    @DisplayName("Get by category")
    public void getByCategoryTest() {
        productDaoJDBC = preparedBase();
        assertEquals(1,productDaoJDBC.getBy(productCategory).size());
    }

    @Test
    @DisplayName("Delete by id from database")
    public void removeTest(){
        productDaoJDBC = preparedBase();
        int idProduct = 1;
        productDaoJDBC.remove(idProduct);
        assertEquals(0, productDaoJDBC.getAll().size());
    }

}

