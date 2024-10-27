package ku.cs.connect;

import ku.cs.models.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class stockEditProductConnect {
    public static void updateProduct(Product product) {
        String query = "UPDATE product SET Product_Name = ?, Quantity = ?, Price = ?, Type = ?, Description = ?, Product_Image = ? WHERE Product_ID = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getProduct_Name());
            stmt.setInt(2, product.getQuantity());
            stmt.setInt(3, product.getPrice());
            stmt.setString(4, product.getType());
            stmt.setString(5, product.getDescription());

            // Update image: Check if selectedImageFile is set or the product's image needs to be updated
            if (product.getImage() != null) {
                stmt.setBinaryStream(6, product.getImage(), (int) product.getImage().available());
            } else {
                stmt.setNull(6, java.sql.Types.BLOB);
            }

            stmt.setString(7, product.getProduct_ID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update product: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
        }
    }
}
