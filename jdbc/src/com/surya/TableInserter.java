package com.surya;
import java.sql.*;
import java.util.Scanner;

public class TableInserter {
    private String user;
    private String pwd;
    private String dbName;
    private String url;
    private Scanner sc;

    public TableInserter(DataBase db, String dbName) throws SQLException {
        this.user = db.getUser();
        this.pwd = db.getPwd();
        this.dbName = dbName;
        this.url = "jdbc:mysql://localhost:3306/" + dbName;
        this.sc = new Scanner(System.in);
    }

    // Method to insert values into a table
    public void insertValues() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.createStatement();
            stmt.executeUpdate("USE " + dbName);

            System.out.print("Enter the name of the table to insert values into: ");
            String tableName = sc.nextLine().trim();

            if (tableName.isEmpty()) {
                System.out.println("Table name cannot be empty.");
                return;
            }

            // Retrieve column names and types
            String getColumnsSQL = "SELECT * FROM " + tableName + " LIMIT 1";
            ResultSet rs = stmt.executeQuery(getColumnsSQL);
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " VALUES (");

            for (int i = 1; i <= columnCount; i++) {
                insertSQL.append("?");
                if (i < columnCount) insertSQL.append(", ");
            }
            insertSQL.append(")");

            PreparedStatement pstmt = conn.prepareStatement(insertSQL.toString());

            // Prompt user for values to insert into each column
            for (int i = 1; i <= columnCount; i++) {
                System.out.print("Enter value for " + metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i) + "): ");
                String inputValue = sc.nextLine();

                // Insert data according to column type
                switch (metaData.getColumnType(i)) {
                    case Types.INTEGER:
                        pstmt.setInt(i, Integer.parseInt(inputValue));
                        break;
                    case Types.VARCHAR:
                    case Types.CHAR:
                        pstmt.setString(i, inputValue);
                        break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                        pstmt.setDouble(i, Double.parseDouble(inputValue));
                        break;
                    case Types.BOOLEAN:
                        pstmt.setBoolean(i, Boolean.parseBoolean(inputValue));
                        break;
                    // Add more data types as needed
                    default:
                        pstmt.setObject(i, inputValue);
                }
            }

            // Execute the insert statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Values inserted successfully into " + tableName + ".");
            } else {
                System.out.println("Insert operation failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}

