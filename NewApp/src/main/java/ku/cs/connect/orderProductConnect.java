package ku.cs.connect;

import ku.cs.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class orderProductConnect {



    public ObservableList<Product> getProductsForOrder(String orderId) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        String query = "SELECT p.Product_ID, p.Product_Name, p.Type, p.Price, ol.Quantity_Order_Line " +
                "FROM product p JOIN order_line ol ON p.Product_ID = ol.Product_ID " +
                "WHERE ol.Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productId = resultSet.getString("Product_ID");
                String productName = resultSet.getString("Product_Name");
                String type = resultSet.getString("Type");
                int price = resultSet.getInt("Price");
                int quantity = resultSet.getInt("Quantity_Order_Line");

                Product product = new Product(productId, productName, quantity, price, type, null, null);
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch products for order: " + e.getMessage());
        }

        return products;
    }

    // เมธอดเพื่ออัปเดตสถานะคำสั่งซื้อเป็น 2
    public void updateOrderStatus(String orderId) {
        String query = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 2); // อัปเดตสถานะเป็น 2
            statement.setString(2, orderId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }
    }
}
