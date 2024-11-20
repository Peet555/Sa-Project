package ku.cs.connect;

import ku.cs.models.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class stockConnect {

    public ObservableList<Product> getAllProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "SELECT Product_ID, Product_Name, Type, Price, Quantity, Description, Product_Image FROM product";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String productID = rs.getString("Product_ID");
                String productName = rs.getString("Product_Name");
                int quantity = rs.getInt("Quantity");
                int productPrice = rs.getInt("Price");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Blob productImageBlob = rs.getBlob("Product_Image");

                InputStream productImageStream = productImageBlob != null ? productImageBlob.getBinaryStream() : null;

                products.add(new Product(productID, productName, quantity, productPrice, type, description, productImageStream));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }

        return products;
    }

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