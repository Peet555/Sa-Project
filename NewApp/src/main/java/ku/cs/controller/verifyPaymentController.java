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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class verifyPaymentController {
    @FXML
    private TableView<PaymentOrder> statusPayment ;
    @FXML
    private TableColumn<PaymentOrder,String> orderID ;
    @FXML
    private TableColumn<PaymentOrder,String> orderType ;
    @FXML
    private TableColumn<PaymentOrder,String> orderStatus ;
    @FXML
    private TableColumn<PaymentOrder,String> orderTime ;
    @FXML
    private Button profileButton;

    @FXML
    public ImageView logo ;

    @FXML
    public void initialize() {
        // กำหนดค่าใน TableColumn โดยใช้ PropertyValueFactory และ String โดยตรง
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderType.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));

        // สร้างข้อมูลจำลอง (Mock Data)
        ObservableList<PaymentOrder> paymentOrders = FXCollections.observableArrayList(
                new PaymentOrder("100012", "สั่งซื้อ", "ชำระแล้ว", "2024-10-11 10:00:00")
        );

        // เพิ่มข้อมูลลงใน TableView
        statusPayment.setItems(paymentOrders);

        statusPayment.setRowFactory(tv -> {
            TableRow<PaymentOrder> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    PaymentOrder rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getOrderID());

                    // เปลี่ยนหน้าไปยังหน้า salerCheckProductPageController
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
                FXRouter.goTo("employeeSellerProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }

    private void changeToProofPage() throws IOException {
        // โหลดหน้าใหม่
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/checkProofPayment.fxml"));
        Parent proofPage = loader.load();

        // ตั้งค่า Scene ใหม่
        Stage stage = (Stage) statusPayment.getScene().getWindow();
        Scene scene = new Scene(proofPage);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goCheckOrder(){
        try {
            FXRouter.goTo("salerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class PaymentOrder {
        private String orderID ;
        private String orderType ;
        private String orderStatus ;
        private String orderTime ;

        public PaymentOrder(String orderID, String orderType, String orderStatus, String orderTime) {
            this.orderID = orderID;
            this.orderType = orderType;
            this.orderStatus = orderStatus;
            this.orderTime = orderTime;
        }

        public String getOrderID() {
            return orderID;
        }

        public String getOrderType() {
            return orderType;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public String getOrderTime() {
            return orderTime;
        }
    }



}
