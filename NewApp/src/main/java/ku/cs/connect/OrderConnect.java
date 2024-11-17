package ku.cs.connect;

import ku.cs.models.Order;
import ku.cs.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderConnect {

    public void saveOrderToDatabase(Order order) {
        String sql = "INSERT INTO `order` (Order_ID, Employee_ID, Customer_ID, Order_Status, Order_Timestamp, Outstanding_Balance, Order_Type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if (order.getOrder_ID().length() > 30) {
                System.out.println("Error: Order_ID too long");
                return;
            }
            pstmt.setString(1, order.getOrder_ID());
            pstmt.setString(2, order.getEmployee_ID());
            pstmt.setString(3, order.getCustomer_ID());
            pstmt.setInt(4, order.getOrder_Status());
            pstmt.setTimestamp(5, order.getOrder_Timestamp());
            pstmt.setInt(6, order.getOutstanding_Balance());
            pstmt.setString(7, order.getOrder_Type());

            pstmt.executeUpdate();
            System.out.println("Order saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving order to database: " + e.getMessage());
        }
    }

    public void saveOrderLineToDatabase(String orderID, List<Product> productList) {
        String sql = "INSERT INTO order_line (Product_ID, Order_ID, Quantity_Order_Line) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (Product product : productList) {
                pstmt.setString(1, product.getProduct_ID());
                pstmt.setString(2, orderID);
                pstmt.setInt(3, product.getQuantity());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Order lines saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving order lines to database: " + e.getMessage());
        }
    }

}
