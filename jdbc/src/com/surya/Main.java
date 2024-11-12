package com.surya;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException{
        Scanner st = new Scanner(System.in);
        Scanner str = new Scanner(System.in);

        // Display instruction to user
        String user = getInput("Enter your Database user name:");
        String pwd = getInput("Enter your Database password: ");

        DataBase db = new DataBase(user, pwd);
        int choice = -1;

        do {
            System.out.println("Enter '1' to Create or Delete a database:");
            System.out.println("Enter '2' to Create table or delete table:");
            System.out.println("Enter '3' to Insert values to Existing tables:");
            System.out.println("Enter '4' to view Existing Table:");
            System.out.println("Enter '0' to exit");
            try {
                choice = st.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                st.nextLine(); // Clears the buffer
                continue; // Skip and asks input again
            }

            switch (choice) {
                case 1:
                    int ch = -1;
                    do {
                        try {
                            System.out.println("Enter '1' to Create Database");
                            System.out.println("Enter '2' to Delete Database");
                            System.out.println("Enter '0' to Back to main Menu");
                            ch = st.nextInt();
                            st.nextLine(); // Clear the buffer
                            switch (ch) {
                                case 1:
                                    System.out.println("Enter the name of the Database to create:");
                                    String name = str.nextLine();
                                    db.createDB(name);
                                    break;
                                case 2:
                                    System.out.println("Enter the name of the database to delete:");
                                    String delDB = str.nextLine();
                                    db.deleteDB(delDB);
                                    break;
                                case 0:
                                    System.out.println("Back to main menu...");
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            st.nextLine(); // Clear the buffer
                        }
                    } while (ch != 0);
                    break;
                case 2:
                    int cha =-1;
                    do {
                        System.out.println("Enter '1' to create table:");
                        System.out.println("Enter '2' to delete table:");
                        System.out.println("Enter .0. to back to Main menu");
                        cha=str.nextInt();
                        st.nextLine();
                        switch (cha){
                            case 1:
                                System.out.println("Enter name of the database to USE");
                                String usename = str.nextLine();
                                Table t = new Table(db, usename); // Pass both db and the database name
                                t.createTable();  // Pass the database name and DataBase object
                                break;
                            case 2:
                                System.out.println("Enter name of the database to USE");
                                String usname= st.nextLine();
                                Table  t1 = new Table(db,usname);
                                t1.deleteTable();
                                break;
                            case 0:
                                System.out.println("Back to Main menu...");
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + cha);
                        }

                    }while (cha!= 0);
                    break;
                case 3:
                    st.nextLine();
                    System.out.println("Enter name of Database have to use");
                    String DBtoUse = str.nextLine();
                    TableInserter Ti = new TableInserter(db,DBtoUse);
                    Ti.insertValues();
                    break;
                case 4:
                    st.nextLine();
                    System.out.println("Enter name of Database have to use");
                    String use = str.nextLine();
                    TableViewer tv = new TableViewer(db,use);
                    tv.viewTable();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter only numbers show in options.");
            }

        } while (choice != 0);

        // Close resources
        st.close();
        str.close();
    }

    // Method to get user info
    private static String getInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        return input;
    }
}

