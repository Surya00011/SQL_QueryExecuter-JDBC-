package com.surya;

import java.sql.*;
import java.util.Scanner;

public class TableViewer {
    private String user;
    private String pwd;
    private String dbName;
    private String url;
    private Scanner sc;

    public TableViewer(DataBase db, String dbName) throws SQLException {
        this.user = db.getUser();
        this.pwd = db.getPwd();
        this.dbName = dbName;
        this.url = "jdbc:mysql://localhost:3306/" + dbName;
        this.sc = new Scanner(System.in);
    }

    // Method to view all records in a table
    public void viewTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish a connection to the database using the credentials
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.createStatement();

            // Use the specified database
            stmt.executeUpdate("USE " + dbName);

            // Prompt user for table name
            System.out.print("Enter the name of the table you want to view: ");
            String tableName = sc.nextLine().trim();

            if (tableName.isEmpty()) {
                System.out.println("Table name cannot be empty.");
                return;
            }

            // SQL query to select all rows from the specified table
            String query = "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(query);

            // Get column metadata to display column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(" "+metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print each row of the table
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("   "+rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
