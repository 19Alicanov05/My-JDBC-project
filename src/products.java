import java.sql.*;
import java.util.Scanner;

public class products {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:postgresql://localhost:5432/MyDataBase";
        String user = "postgres";
        String password = "1905";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            while (true) {
                System.out.println("1. Products");
                System.out.println("2. New products added");
                System.out.println("3. Change product");
                System.out.println("4. Delete product");
                System.out.println("5. Exit");

                int secim = scanner.nextInt();
                scanner.nextLine();

                switch (secim) {
                    case 1:
                        getProducts(conn);
                        break;
                    case 2:
                        addProducts(conn, scanner);
                        break;
                    case 3:
                        changeProducts(conn, scanner);
                        break;
                    case 4:
                        deleteProducts(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exited...");
                        conn.close();
                        return;
                    default:
                        System.out.println("Wrong choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM products";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Products:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Expiry Date: " + rs.getDate("expiry_date") +
                    ", Price: " + rs.getDouble("price") +
                    ", Count: " + rs.getInt("count"));
        }
        rs.close();
    }

    public static void addProducts(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Product name: ");
        String name = scanner.nextLine();

        System.out.print("Expiry date (YYYY-MM-DD): ");
        String expiryDate = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Count: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO products (name, expiry_date, price, count) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setDate(2, Date.valueOf(expiryDate));
        pstmt.setDouble(3, price);
        pstmt.setInt(4, count);

        pstmt.executeUpdate();
        System.out.println("New product added.");
    }

    public static void changeProducts(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Change product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New product name: ");
        String newName = scanner.nextLine();

        System.out.print("New expiry date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine();

        System.out.print("New price: ");
        double newPrice = scanner.nextDouble();

        System.out.print("New count: ");
        int newCount = scanner.nextInt();
        scanner.nextLine();

        String sql = "UPDATE products SET name = ?, expiry_date = ?, price = ?, count = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setDate(2, Date.valueOf(newDate));
        pstmt.setDouble(3, newPrice);
        pstmt.setInt(4, newCount);
        pstmt.setInt(5, id);

        pstmt.executeUpdate();
        System.out.println("Product updated.");
    }

    public static void deleteProducts(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Delete product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();
        System.out.println("Product deleted.");
    }
}
