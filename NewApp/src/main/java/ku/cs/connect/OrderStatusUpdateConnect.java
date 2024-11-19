package ku.cs.connect;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public boolean updateOrderAndCreateInvoice(String orderId) {
        try (Connection connection = DatabaseConnect.getConnection()) {
            connection.setAutoCommit(false);

            // อัปเดตสถานะ Order
            String updateOrderStatusQuery = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateOrderStatusQuery)) {
                updateStmt.setInt(1, 2); // เปลี่ยนสถานะเป็นรอชำระเงิน
                updateStmt.setString(2, orderId);
                updateStmt.executeUpdate();
            }

            // คำนวณ Invoice_Price
            String totalQuery = """
            SELECT SUM(p.Price * ol.Quantity_Order_Line) AS total
            FROM Order_Line ol
            JOIN product p ON ol.Product_ID = p.Product_ID
            WHERE ol.Order_ID = ?
        """;
            int invoicePrice = 0;
            try (PreparedStatement totalStmt = connection.prepareStatement(totalQuery)) {
                totalStmt.setString(1, orderId);
                ResultSet rs = totalStmt.executeQuery();
                if (rs.next()) {
                    invoicePrice = rs.getInt("total");
                } else {
                    System.out.println("No order line items found for Order_ID: " + orderId);
                    return false;
                }
            }

            // สร้างข้อมูล Invoice
            String insertInvoiceQuery = """
            INSERT INTO invoice (Invoice_ID, Order_ID, Invoice_Price, Invoice_Timestamp, Status_pay)
            VALUES (?, ?, ?, ?, ?)
        """;
            try (PreparedStatement insertStmt = connection.prepareStatement(insertInvoiceQuery)) {
                insertStmt.setString(1, UUID.randomUUID().toString().replace("-", "")); // UUID สำหรับ Invoice_ID
                insertStmt.setString(2, orderId);
                insertStmt.setDouble(3, invoicePrice);
                insertStmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // เวลาปัจจุบัน
                insertStmt.setInt(5, 2); // รอชำระเงิน
                insertStmt.executeUpdate();
            }

            connection.commit(); // คอมมิทการเปลี่ยนแปลง
            return true;

        } catch (SQLException e) {
            System.err.println("Error updating order and creating invoice: " + e.getMessage());
            return false;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean updateRefuseOrder(String orderId) throws SQLException {
    try (Connection connection = DatabaseConnect.getConnection()) {
        connection.setAutoCommit(false);

        // อัปเดตสถานะ Order
        String updateOrderStatusQuery = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateOrderStatusQuery)) {
            updateStmt.setInt(1, 0); // เปลี่ยนสถานะเป็นรอชำระเงิน
            updateStmt.setString(0, orderId);
            updateStmt.executeUpdate();
        }
    }
    return false;
}



}
