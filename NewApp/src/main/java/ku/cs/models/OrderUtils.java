package ku.cs.models;

public class OrderUtils {

    // Method for converting Order_Status based on Order_Type
    public static String getOrderStatus(String orderType, int status) {
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
