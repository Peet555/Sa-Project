package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class productController {

    @FXML
    public Button buttonOrderList;

    @FXML
    public VBox vBox;

    @FXML
    public Button homeButton;

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;
    private Product product;
    @FXML
    public void initialize() throws IOException {
        addTypeProductItem(1);
        String id = (String) FXRouter.getData();

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });

        // เปลี่ยนจากการไปที่หน้าใหม่เป็นเปิด Modal ของ productQualityWindow
        buttonOrderList.setOnAction(event -> {
            try {
                openProductQualityWindow();  // เรียกใช้ method เปิด Modal
            } catch (IOException e) {
                System.err.println("Cannot go to productQualityWindow");
            }
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("profile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

        cartButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderList");
            } catch (IOException e) {
                System.err.println("Cannot go to cart");
            }
        });

        orderHistoryButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderHistory");
            } catch (IOException e) {
                System.err.println("Cannot go to order history");
            }
        });
    }

    public void addTypeProductItem(int index) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/view/productItem.fxml"));
        VBox p = fxmlLoader.load();
        productItemController controller = fxmlLoader.getController();

        //controller.productName.setText();
        vBox.getChildren().add(p);
    }

    // Method เพื่อเปิดหน้าต่าง modal ที่เป็น ProductQualityWindow
    private void openProductQualityWindow() throws IOException {
        // โหลดหน้าต่าง Modal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/productQualityWindow.fxml"));
        VBox vbox = loader.load();

        // สร้าง Stage ใหม่เพื่อแสดง Modal
        Stage modalStage = new Stage();
        modalStage.setTitle("Product Quality Window");
        modalStage.initModality(Modality.APPLICATION_MODAL);  // ทำให้หน้าต่างนี้เป็น Modal
        modalStage.setScene(new Scene(vbox));  // กำหนด Scene ให้กับ Stage ใหม่
        modalStage.showAndWait();  // แสดง Modal และรอให้ปิดก่อนกลับมาทำงานต่อ
    }
}
