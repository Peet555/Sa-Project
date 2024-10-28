package ku.cs.connect;

import ku.cs.controller.salerCheckOrderPageController.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class sellerCheckOrderConnect {

    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        // คำสั่ง SQL ดึงเฉพาะฟิลด์ที่ต้องการ
        String query = "SELECT Order_ID, Order_Type, Order_Status, Order_Timestamp FROM `order`";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // วนลูปเพื่ออ่านข้อมูลแต่ละแถวจาก ResultSet และเพิ่มใน ObservableList
            while (resultSet.next()) {
                String orderID = resultSet.getString("Order_ID");
                String orderType = resultSet.getString("Order_Type");
                int status = resultSet.getInt("Order_Status");
                String orderTimestamp = resultSet.getString("Order_Timestamp");

                // แปลง Order_Status จากตัวเลขเป็นข้อความ
                String orderStatus;
                switch (status) {
                    case 1:
                        orderStatus = "รอยืนยัน";
                        break;
                    case 2:
                        orderStatus = "รอชำระเงิน";
                        break;
                    case 3:
                        orderStatus = "ชำระเงินแล้ว";
                        break;
                    case 4:
                        orderStatus = "กำลังจัดส่ง";
                        break;
                    case 5:
                        orderStatus = "ได้รับของแล้ว";
                        break;
                    default:
                        orderStatus = "สถานะไม่ทราบ";
                        break;
                }

                // เพิ่มข้อมูลลงในรายการ orders
                orders.add(new Order(orderID, orderType, orderStatus, orderTimestamp));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
        }

        return orders;
    }
}
