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
}
