package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    public void initialize() throws IOException {
        addTypeProductItem(1);

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
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
    }

    public void addTypeProductItem(int index) throws IOException {
        VBox p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ku/cs/view/productItem.fxml")));
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
