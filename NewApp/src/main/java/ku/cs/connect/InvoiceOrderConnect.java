package ku.cs.connect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ku.cs.controller.checkProofPaymentController.ProductSale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceOrderConnect {

    public static class ProductSalesData {
        public ObservableList<ProductSale> productSales;
        public byte[] paymentImageData;

        public ProductSalesData(ObservableList<ProductSale> productSales, byte[] paymentImageData) {
            this.productSales = productSales;
            this.paymentImageData = paymentImageData;
        }
    }

    public static ProductSalesData loadProductSales(String invoiceID) {
        ObservableList<ProductSale> productSales = FXCollections.observableArrayList();
        byte[] paymentImageData = null;

        String query = "SELECT p.Product_ID, p.Product_Name, ol.Quantity_Order_Line, p.Price, " +
                "(ol.Quantity_Order_Line * p.Price) AS TotalPrice, i.Payment_Image " +
                "FROM Order_Line ol " +
                "JOIN Product p ON ol.Product_ID = p.Product_ID " +
                "JOIN `order` o ON ol.Order_ID = o.Order_ID " +
                "JOIN invoice i ON o.Order_ID = i.Order_ID " +
                "WHERE i.Invoice_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, invoiceID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("Quantity_Order_Line");
                    int productPrice = resultSet.getInt("Price");
                    int totalPrice = resultSet.getInt("TotalPrice");

                    productSales.add(new ProductSale(
                            resultSet.getString("Product_ID"),
                            resultSet.getString("Product_Name"),
                            quantity,
                            productPrice,
                            totalPrice
                    ));

                    // โหลด Payment_Image ครั้งเดียว
                    if (paymentImageData == null) {
                        paymentImageData = resultSet.getBytes("Payment_Image");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading product sales: " + e.getMessage());
        }

        return new ProductSalesData(productSales, paymentImageData);
    }

    public static boolean updateInvoiceStatus(String invoiceID) {
        String selectOrderTypeQuery = "SELECT o.Order_ID, Order_Type, Status_Pay " +
                "FROM invoice i " +
                "JOIN `order` o ON i.Order_ID = o.Order_ID " +
                "WHERE i.Invoice_ID = ?";
        String updateInvoiceQuery = "UPDATE invoice SET Status_Pay = ? WHERE Invoice_ID = ?";
        String updateOrderStatusQuery = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection()) {
            try (PreparedStatement selectStatement = connection.prepareStatement(selectOrderTypeQuery)) {
                selectStatement.setString(1, invoiceID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String orderType = resultSet.getString("Order_Type");
                        int statusPay = resultSet.getInt("Status_Pay");
                        String orderID = resultSet.getString("Order_ID");

                        int newStatusPay = -1;
                        if ("สั่งจอง".equals(orderType) && statusPay == 4) {
                            newStatusPay = 5; // สั่งจอง -> ชำระค่ามัดจำแล้ว
                        } else if ("สั่งซื้อ".equals(orderType) && statusPay == 2) {
                            newStatusPay = 3; // สั่งซื้อ -> ชำระเงินแล้ว
                        } else if ("สั่งจอง".equals(orderType) && statusPay == 2) {
                            newStatusPay = 3; // สั่งจอง -> ชำระแล้ว
                        }

                        if (newStatusPay != -1) {
                            // อัปเดต Status_Pay ในตาราง Invoice
                            try (PreparedStatement updateInvoiceStatement = connection.prepareStatement(updateInvoiceQuery)) {
                                updateInvoiceStatement.setInt(1, newStatusPay);
                                updateInvoiceStatement.setString(2, invoiceID);
                                updateInvoiceStatement.executeUpdate();
                            }

                            // อัปเดต Order_Status ในตาราง Order
                            try (PreparedStatement updateOrderStatement = connection.prepareStatement(updateOrderStatusQuery)) {
                                updateOrderStatement.setInt(1, newStatusPay);
                                updateOrderStatement.setString(2, orderID);
                                updateOrderStatement.executeUpdate();
                                System.out.println("อัปเดตสถานะของ Order สำเร็จ");
                            }
                            return true; // อัปเดตสำเร็จ
                        } else {
                            System.out.println("ไม่สามารถอัปเดตสถานะได้เนื่องจากเงื่อนไขไม่ตรง");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("เกิดข้อผิดพลาดในการอัปเดตสถานะ: " + e.getMessage());
        }
        return false; // อัปเดตล้มเหลว
    }



}
