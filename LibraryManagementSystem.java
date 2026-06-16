import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    static final String USER = "root";
    static final String PASSWORD = "password";

    static Scanner sc = new Scanner(System.in);

    // Add Book
    public static void addBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            String query =
                    "INSERT INTO books(book_id,title,author,issued) VALUES(?,?,?,false)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);

            ps.executeUpdate();

            System.out.println("Book added successfully!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Search Book
    public static void searchBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            String query = "SELECT * FROM books WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Book ID : " + rs.getInt("book_id"));
                System.out.println("Title   : " + rs.getString("title"));
                System.out.println("Author  : " + rs.getString("author"));
                System.out.println("Issued  : " + rs.getBoolean("issued"));
            } else {
                System.out.println("Book not found!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Issue Book
    public static void issueBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            String query =
                    "UPDATE books SET issued=true WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Book issued successfully!");
            else
                System.out.println("Book not found!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Return Book
    public static void returnBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            String query =
                    "UPDATE books SET issued=false WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Book returned successfully!");
            else
                System.out.println("Book not found!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    searchBook();
                    break;

                case 3:
                    issueBook();
                    break;

                case 4:
                    returnBook();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}
