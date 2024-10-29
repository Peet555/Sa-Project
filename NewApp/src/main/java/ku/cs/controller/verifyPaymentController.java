package ku.cs.controller;

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

public class verifyPaymentController {
    @FXML
    private TableView<Invoice> Invoice_Table;
    @FXML
    private TableColumn<Invoice, String> orderID;
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

        ObservableList<Invoice> invoices = loadInvoicesFromDatabase();
        Invoice_Table.setItems(invoices);

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
        String query = "SELECT Invoice_ID, Order_ID, Invoice_Price, Invoice_Timestamp, Status_pay, Payment_Image FROM invoice";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String statusPay = convertStatusToString(resultSet.getInt("Status_pay"));

                invoices.add(new Invoice(
                        resultSet.getString("Invoice_ID"),
                        resultSet.getString("Order_ID"),
                        resultSet.getInt("Invoice_Price"),
                        resultSet.getString("Invoice_Timestamp"),
                        statusPay, // Use the converted status pay
                        resultSet.getBytes("Payment_Image") // Use "Payment_Image" instead of null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading invoices: " + e.getMessage());
        }

        return invoices;
    }

    private String convertStatusToString(int status) {
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
