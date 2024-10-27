package ku.cs.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class stockAddProductConnect {

    public void addProduct(String productId, String name, int quantity, String type, double price, String description, byte[] image) {
        String query = "INSERT INTO product (product_id, product_name, quantity, type, price, description, product_image) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productId);
            stmt.setString(2, name);
            stmt.setInt(3, quantity);
            stmt.setString(4, type);
            stmt.setDouble(5, price);
            stmt.setString(6, description);
            stmt.setBytes(7, image); // เก็บรูปภาพเป็น BLOB

            stmt.executeUpdate();
            System.out.println("Product added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }
}
