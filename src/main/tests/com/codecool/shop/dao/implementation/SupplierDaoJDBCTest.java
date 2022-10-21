package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoJDBCTest {
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();
    private Supplier supplier = new Supplier("Lenovo", "Computers");

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

    private SupplierDaoJDBC preparedBase() {
        supplierDaoJDBC.add(supplier);
        return supplierDaoJDBC;
    }

    @Test
    @DisplayName("Adding to database")
    void addTest() {
        supplierDaoJDBC = preparedBase();
        assertEquals(1, supplierDaoJDBC.getAll().size());
    }

    @Test
    @DisplayName("Find by id")
    public void findByIdTest() {
        supplierDaoJDBC = preparedBase();
        int idSupplier = 1;
        Supplier supplierFromDatabase = supplierDaoJDBC.find(idSupplier);
        assertEquals(idSupplier, supplierFromDatabase.getId());
    }

    @Test
    @DisplayName("Find by id(not exists)")
    public void findByNotExistsIdTest() {
        supplierDaoJDBC = preparedBase();
        int idSupplier = 999;
        assertNull(supplierDaoJDBC.find(idSupplier));
    }

    @Test
    @DisplayName("Delete by id from database")
    public void removeTest(){
        supplierDaoJDBC = preparedBase();
        int idProduct = 1;
        supplierDaoJDBC.remove(idProduct);
        assertEquals(0, supplierDaoJDBC.getAll().size());
    }

    @Test
    @DisplayName("Find by name")
    void findByNameTest() {
        supplierDaoJDBC = preparedBase();
        String nameSupplier = supplier.getName();
        Supplier supplier1 = supplierDaoJDBC.findByName(nameSupplier);
        assertEquals(supplier.getName(), supplier1.getName() );
    }

    @Test
    @DisplayName("Find by name(not exists)")
    void findByNotExistsNameTest() {
        supplierDaoJDBC = preparedBase();
        String nameSupplier = "qwerty";
        assertNull(supplierDaoJDBC.findByName(nameSupplier));
    }

    @Test
    @DisplayName("Get all records database")
    public void getAllTest() {
        supplierDaoJDBC = preparedBase();
        Supplier supplier1 = new Supplier("Lenovo", "Ebook");
        supplierDaoJDBC.add(supplier1);
        assertEquals(2, supplierDaoJDBC.getAll().size());
    }
}