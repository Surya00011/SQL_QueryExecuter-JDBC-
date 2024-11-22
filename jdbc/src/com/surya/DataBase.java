package com.surya;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public String url="jdbc:mysql://localhost:3306/";
    private String user="";
    private String pwd="";

    public DataBase(String user, String pwd) throws SQLException {
        this.user = user;
        this.pwd = pwd;
    }
    public DataBase(){

    }
    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }
    public void createDB(String nameOfDB){
        String DbName;
        DbName = nameOfDB;
        Connection conn = null;
        Statement  stmt = null;
        try{
             conn = DriverManager.getConnection(url,user,pwd);
             stmt = conn.createStatement();
            String query ="CREATE DATABASE "+ DbName +';';
            int cd = stmt.executeUpdate(query);

            System.out.println(DbName +" Database is created Successfully ");

        }
        catch (SQLException e){
            System.out.println("Failed to create database"+e.getMessage());
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void deleteDB(String nameOfDB){
        String DbName;
        DbName = nameOfDB;
        Connection conn = null;
        Statement  stmt = null;
        try{
            conn = DriverManager.getConnection(url,user,pwd);
            stmt = conn.createStatement();
            String query ="DROP DATABASE "+ DbName +';';
            int dd = stmt.executeUpdate(query);
            if(dd==0){
                System.out.println("DATABASE "+ DbName +" is deleted Successfully ");
            }
            else {
                System.out.println("Failed to delete database");
            }
            stmt.close();
            conn.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
