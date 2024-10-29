package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ku.cs.connect.DatabaseConnect;
import ku.cs.models.Invoice;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class verifyPaymentController {
    @FXML
    private TableView<Invoice> Invoice_Table;
    @FXML
    private TableColumn<Invoice, String> orderID;
    @FXML
    private TableColumn<Invoice, String> Order_Type;
    @FXML
    private TableColumn<Invoice, String> Invoice_ID;
    @FXML
    private TableColumn<Invoice, String> Status_Pay;
    @FXML
    private TableColumn<Invoice, String> Invoice_Timestamp;
    @FXML
    private Button profileButton;

    @FXML
    public void initialize() {
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        Invoice_ID.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
        Status_Pay.setCellValueFactory(new PropertyValueFactory<>("statusPay"));
        Invoice_Timestamp.setCellValueFactory(new PropertyValueFactory<>("invoiceTimestamp"));

        // สร้างคอลัมน์ Order_Type โดยไม่ต้องมีฟิลด์ใน Invoice
        Order_Type.setCellValueFactory(cellData -> {
            String orderId = cellData.getValue().getOrderID();
            String orderType = getOrderType(orderId); // เรียกใช้งาน getOrderType
            return new SimpleStringProperty(orderType);
        });

        ObservableList<Invoice> invoices = loadInvoicesFromDatabase();
        Invoice_Table.setItems(invoices);

        // ตั้งค่าการคลิกที่แถว
        Invoice_Table.setRowFactory(tv -> {
            TableRow<Invoice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        changeToProofPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeSellerProfile");
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }



    private ObservableList<Invoice> loadInvoicesFromDatabase() {
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();
        String query = "SELECT i.Invoice_ID, i.Order_ID, i.Invoice_Price, i.Invoice_Timestamp, i.Status_pay, i.Payment_Image, o.Order_Type " +
                "FROM invoice i " +
                "JOIN `order` o ON i.Order_ID = o.Order_ID"; // Join with order table

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




    private String getOrderType(String orderId) {
        String orderType = null;
        String query = "SELECT Order_Type FROM `order` WHERE Order_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderType = resultSet.getString("Order_Type");
                    System.out.println("Order_Type for Order_ID " + orderId + ": " + orderType);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving order type: " + e.getMessage());
        }

        return orderType;
    }


    private String convertStatusToString(String orderType, int status) {
        if ("สั่งจอง".equals(orderType)) {
            // กรณี Order_Type เป็น "สั่งจอง"
            switch (status) {
                case 1:
                    return "รอยืนยัน";
                case 2:
                    return "รอชำระค่ามัดจำ";
                case 3:
                    return "ชำระค่ามัดจำแล้ว";
                case 4:
                    return "รอสินค้าเข้าคลัง";
                case 5:
                    return "ชำระยอดคงเหลือ";
                case 6:
                    return "ชำระแล้ว";
                case 7:
                    return "กำลังจัดส่ง";
                case 8:
                    return "ได้รับของแล้ว";
                default:
                    return "สถานะไม่ทราบ";
            }
        } else {
            // กรณี Order_Type เป็น "สั่งซื้อ"
            switch (status) {
                case 1:
                    return "รอยืนยัน";
                case 2:
                    return "รอชำระเงิน";
                case 3:
                    return "ชำระเงินแล้ว";
                case 4:
                    return "กำลังจัดส่ง";
                case 5:
                    return "ได้รับของแล้ว";
                default:
                    return "สถานะไม่ทราบ";
            }
        }
    }






    private void changeToProofPage() throws IOException {
        Invoice selectedInvoice = Invoice_Table.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/checkProofPayment.fxml"));
            Parent proofPage = loader.load();

            checkProofPaymentController controller = loader.getController();
            controller.setInvoiceID(selectedInvoice.getInvoiceID());

            Stage stage = (Stage) Invoice_Table.getScene().getWindow();
            stage.setScene(new Scene(proofPage));
            stage.show();
        }
    }

    @FXML
    public void goCheckOrder() {
        try {
            FXRouter.goTo("salerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
