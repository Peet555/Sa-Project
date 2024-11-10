package ku.cs.connect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ku.cs.models.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceDataConnect {

    public static ObservableList<Invoice> loadInvoicesFromDatabase() {
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();
        String query = "SELECT i.Invoice_ID, i.Order_ID, i.Invoice_Price, i.Invoice_Timestamp, i.Status_pay, i.Payment_Image, o.Order_Type " +
                "FROM invoice i " +
                "JOIN `order` o ON i.Order_ID = o.Order_ID";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String orderId = resultSet.getString("Order_ID");
                String orderType = resultSet.getString("Order_Type");

                invoices.add(new Invoice(
                        resultSet.getString("Invoice_ID"),
                        orderId,
                        resultSet.getInt("Invoice_Price"),
                        resultSet.getString("Invoice_Timestamp"),
                        convertStatusToString(orderType, resultSet.getInt("Status_pay")),
                        resultSet.getBytes("Payment_Image")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error loading invoices: " + e.getMessage());
        }

        return invoices;
    }

    public static String getOrderType(String orderId) {
        String orderType = null;
        String query = "SELECT Order_Type FROM `order` WHERE Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderType = resultSet.getString("Order_Type");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving order type: " + e.getMessage());
        }

        return orderType;
    }

    private static String convertStatusToString(String orderType, int status) {
        if ("สั่งจอง".equals(orderType)) {
            switch (status) {
                case 1: return "รอยืนยัน";
                case 2: return "รอชำระค่ามัดจำ";
                case 3: return "รอสินค้าเข้าคลัง";
                case 4: return "ชำระยอดคงเหลือ";
                case 5: return "ชำระแล้ว";
                case 6: return "กำลังจัดส่ง";
                case 7: return "ได้รับของแล้ว";
                default: return "สถานะไม่ทราบ";
            }
        } else {
            switch (status) {
                case 1: return "รอยืนยัน";
                case 2: return "รอชำระเงิน";
                case 3: return "ชำระเงินแล้ว";
                case 4: return "กำลังจัดส่ง";
                case 5: return "ได้รับของแล้ว";
                default: return "สถานะไม่ทราบ";
            }
        }
    }
}
