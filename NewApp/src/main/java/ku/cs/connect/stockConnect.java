package ku.cs.connect;

import ku.cs.models.Product;
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
}
