package com.example.crud.dao;

import com.example.crud.model.Supplier;

import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier);

    Supplier find(int id);

    void remove(int id);

    Supplier findByName(String name);

    List<Supplier> getAll();
}
