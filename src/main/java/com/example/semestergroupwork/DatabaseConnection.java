package com.example.semestergroupwork;
/*
    TEAM NAME: AZTECH
    1. BAWAH ABUBA IBRAHIM - 10863084 - PROGRAMMER
    2. BAWAH MANSURA-10911156- DATABASE ADMIN
    3. GYENI PRISCILLA- 10865576-PROJECT MANAGER
    4. HAYIBOR CINDY -10905039 -UI/UX DESIGNER
    5. SAMUEL AMPONSAH - 10922693 - UI/UX DESIGNER

 */

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
