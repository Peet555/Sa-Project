package ku.cs.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusUpdateConnect {
    public void updateOrderStatus(String orderId, int newStatus) {
        String updateQuery = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setInt(1, newStatus);
            statement.setString(2, orderId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Order status updated successfully.");
            } else {
                System.out.println("Order status update failed.");
            }

        } catch (SQLException e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }
    }
    public int getOrderStatus(String orderId) {
        String query = "SELECT Order_Status FROM `order` WHERE Order_ID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("Order_Status");
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve order status: " + e.getMessage());
        }
        return -1; // ส่งค่า -1 ถ้ามีปัญหาในการดึงข้อมูล
    }

    public String getOrderType(String orderId) {
        String query = "SELECT Order_Type FROM `order` WHERE Order_ID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Order_Type");
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve order type: " + e.getMessage());
        }
        return null; // ส่งค่า null ถ้ามีปัญหาในการดึงข้อมูล
    }


}
