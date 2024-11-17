package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ku.cs.connect.InvoiceDataConnect;
import ku.cs.models.Invoice;
import ku.cs.services.FXRouter;

import java.io.IOException;

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
    private ComboBox<String> statusComboBox;

    private ObservableList<Invoice> invoices; // เก็บข้อมูลทั้งหมด
    @FXML
    public void initialize() {

        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        Invoice_ID.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
        Status_Pay.setCellValueFactory(new PropertyValueFactory<>("statusPay"));
        Invoice_Timestamp.setCellValueFactory(new PropertyValueFactory<>("invoiceTimestamp"));

        // สร้างคอลัมน์ Order_Type โดยใช้ SimpleStringProperty
        Order_Type.setCellValueFactory(cellData -> {
            String orderType = cellData.getValue().getOrderType();
            return new SimpleStringProperty(orderType);
        });

        // โหลดข้อมูล (สมมติว่า InvoiceDataConnect.loadInvoicesFromDatabase() ดึงข้อมูลทั้งหมดแล้ว)
        invoices = FXCollections.observableArrayList(InvoiceDataConnect.loadInvoicesFromDatabase());
        Invoice_Table.setItems(invoices);

        // ตั้งค่า ComboBox
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "ทั้งหมด","รอชำระเงิน", "รอชำระค่ามัดจำ", "ชำระเงินแล้ว", "ชำระยอดคงเหลือ"
        );
        statusComboBox.setItems(statusOptions);

        // Listener สำหรับกรองข้อมูล
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("ทั้งหมด")) {
                    // แสดงข้อมูลทั้งหมด
                    Invoice_Table.setItems(invoices);
                } else {
                    // กรองข้อมูลตามค่า Status_Pay ที่เลือก
                    ObservableList<Invoice> filteredInvoices = invoices.filtered(
                            invoice -> invoice.getStatusPay().equals(newValue)
                    );
                    Invoice_Table.setItems(filteredInvoices);
                }
            }
        });

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
