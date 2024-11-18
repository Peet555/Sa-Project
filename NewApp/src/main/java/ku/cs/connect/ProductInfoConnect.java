package ku.cs.connect;

import ku.cs.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoConnect {

    public Product productInfo(String id) {

        String query = "SELECT Product_Name,Price,Quantity,Description,product_Image  FROM product WHERE Product_ID = ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String productName = rs.getString("Product_Name");
                    int price  = rs.getInt("Price");
                    int quantity  = rs.getInt("Quantity");
                    String description = rs.getString("Description");
                    byte[] productImageByte  = rs.getBytes("Product_Image");
                    return new Product(productName, price, quantity, description, productImageByte);
                }
                    System.out.println("ได้");
                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            System.out.println("Error loading products by type: " + e.getMessage());
        }
        return null;
    }

    public boolean updateProductQuantity(String productId, int quantityToReduce) {
        String query = "UPDATE product SET Quantity = Quantity - ? WHERE Product_ID = ? AND Quantity >= ?";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, quantityToReduce);
            ps.setString(2, productId);
            ps.setInt(3, quantityToReduce); // ตรวจสอบว่ามีจำนวนสินค้าเพียงพอ
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // คืนค่าความสำเร็จ
        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());
            return false;
        }
    }


}
