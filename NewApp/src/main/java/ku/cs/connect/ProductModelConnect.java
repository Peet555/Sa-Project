package ku.cs.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ku.cs.models.Product;

public class ProductModelConnect {

    public List<Product> loadProducts(String typeName) {
            List<Product> products = new ArrayList<>();
            String query = "SELECT Product_ID,Product_Name,Price,product_Image  FROM product WHERE Type = ?";

            try (Connection conn = DatabaseConnect.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, typeName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product(
                                rs.getString("Product_ID"),
                                rs.getString("Product_Name"),
                                rs.getInt("Price"),
                                rs.getBytes("Product_Image")
                        );
                        products.add(product);
                        System.out.println("Product loaded: " + product.getProduct_ID() + product.getProduct_Name() + ", " + product.getPrice());
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error loading products by type: " + e.getMessage());
            }
            return products;
        }
}
