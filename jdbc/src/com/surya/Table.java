package com.surya;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Table {
    private String user;
    private String pwd;
    private String nameDB;
    private String url;

    public Table(DataBase db, String nameDB) throws SQLException {
        this.user = db.getUser();
        this.pwd = db.getPwd();
        this.nameDB = nameDB;
        this.url = "jdbc:mysql://localhost:3306/" + nameDB;
    }

    // Method to create a table based on user input
    public void createTable() {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish a connection to the database using the credentials
            conn = DriverManager.getConnection(url, user, pwd);

            // Use the specified database
            stmt = conn.createStatement();
            stmt.executeUpdate("USE " + nameDB);

            // Get table details from the user
            System.out.print("Enter the name of the table you want to create: ");
            String tableName = sc.nextLine();

            System.out.print("Enter the number of columns for the table: ");
            int numColumns = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            StringBuilder createTableSQL = new StringBuilder("CREATE TABLE " + tableName + " (");

            // Get column details from the user
            for (int i = 1; i <= numColumns; i++) {
                System.out.print("Enter the name of column " + i + ": ");
                String columnName = sc.nextLine();

                System.out.print("Enter the data type of column " + i + ": ");
                String dataType = sc.nextLine();

                // Append column definition to the (create_table_sql) string
                createTableSQL.append(columnName).append(" ").append(dataType);

                // Add a comma unless it's the last column
                if (i != numColumns) {
                    createTableSQL.append(", ");
                }
            }

            createTableSQL.append(");");

            // Execute the CREATE TABLE SQL statement
            int res = stmt.executeUpdate(createTableSQL.toString());
            if (res == 0) {
                System.out.println("Table " + tableName + " created successfully in " + nameDB + " database.");
            } else {
                System.out.println("Table creation failed");
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
    public void deleteTable() {
        Connection conn = null;
        Statement stmt = null;
        Scanner sc = new Scanner(System.in);
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.createStatement();
            stmt.executeUpdate("USE " + nameDB);

            System.out.print("Enter the name of the table to delete: ");
            String tableName = sc.nextLine();

            String deleteTableSQL = "DROP TABLE IF EXISTS " + tableName;

            int res = stmt.executeUpdate(deleteTableSQL);
            if (res == 0) {
                System.out.println("Table " + tableName + " deleted successfully from " + nameDB + " database.");
            } else {
                System.out.println("Table deletion failed.");
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
