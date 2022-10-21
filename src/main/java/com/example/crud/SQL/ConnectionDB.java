package com.example.crud.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {

    private static final String DATABASE = "jdbc:mysql://localhost:3306/codecoolshop?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static ConnectionDB instance = null;

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }


    public static Connection getConnection() {
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection(
                    DATABASE,
                    DB_USER,
                    DB_PASSWORD);
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public int executeUpdate(String query, List<Object> parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        int idProduct = 0;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            statement.execute();
            res = statement.getResultSet();
            if (res.next()) {
                idProduct = res.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idProduct;
    }

    public List<Map<String, Object>> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            statement.executeQuery();
            resultSet = statement.getResultSet();
            int numOfCols = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();

                for (int i = 0; i < numOfCols; i++) {
                    data.put(resultSet.getMetaData().getColumnName(i + 1),
                            resultSet.getObject(i + 1));
                }

                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

}
