package Database.SQLite;

import Database.Database;
import Product.Product;

import java.sql.*;

//public class SQLite - in this class, new database is created. The database named 'productsDB' contains products with information about them (price, barcode)
public class SQLite {

    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:productsDB.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE PRODUCTS_LIST" +
                    "(ID TEXT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:productsDB.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void insertToProductsList(String id, String name, double price) {
        String sql = "INSERT INTO PRODUCTS_LIST (ID, NAME, PRICE) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Product selectProduct (String barcode) {
        String sql = "SELECT id, name, price "
                + "FROM PRODUCTS_LIST WHERE id=?";

        try (Connection conn = SQLite.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, barcode);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            Product result;
            return result = new Product(rs.getString("name"), rs.getDouble("price"), rs.getString("id"));

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        } return null;

    }
}

