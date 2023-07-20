package com.example.semestergroupwork;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
        // Database login details, you can change them according to the your setup
    public Connection getConnection(){
        String databaseName = "pharmacy";
        String databaseUser = "root";
        String databasePassword = "aztech";
        String url = "jdbc:mysql://localhost:3306/"+ databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}
