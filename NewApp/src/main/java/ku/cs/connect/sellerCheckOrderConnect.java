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
                String orderStatus = getOrderStatus(orderType, status);

                // เพิ่มข้อมูลลงในรายการ orders
                orders.add(new Order(orderID, orderType, orderStatus, orderTimestamp));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
        }

        return orders;
    }

    // ฟังก์ชันสำหรับแปลง Order_Status ขึ้นอยู่กับ Order_Type
    private String getOrderStatus(String orderType, int status) {
        switch (orderType) {
            case "สั่งซื้อ": // Purchase Order
                switch (status) {
                    case 1: return "รอยืนยัน";
                    case 2: return "รอชำระเงิน";
                    case 3: return "ชำระเงินแล้ว";
                    case 4: return "กำลังจัดส่ง";
                    case 5: return "ได้รับของแล้ว";
                    default: return "สถานะไม่ทราบ";
                }
            case "สั่งจอง": // Reservation Order
                switch (status) {
                    case 1: return "รอยืนยัน";
                    case 2: return "รอชำระค่ามัดจำ";
                    case 3: return "ชำระค่ามัดจำแล้ว";
                    case 4: return "รอสินค้าเข้าคลัง";
                    case 5: return "ชำระยอดคงเหลือ";
                    case 6: return "ชำระแล้ว";
                    case 7: return "กำลังจัดส่ง";
                    case 8: return "ได้รับของแล้ว";
                    default: return "สถานะไม่ทราบ";
                }
            default:
                return "สถานะไม่ทราบ";
        }
    }
}
