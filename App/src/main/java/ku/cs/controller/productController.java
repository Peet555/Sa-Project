package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class productController {

    @FXML
//    public Button buttonOrder;
//    public Button buttonPreOrder;
    public VBox vBox;

    @FXML
    public Button homeButton;

    public void initialize() throws IOException {
        addTypeProductItem(1);

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println();
            }
        });

//        buttonPreOrder.setOnAction(event -> {
//            try {
//                showPreOrderWindow();
//            } catch (IOException e) {
//                System.err.println("Failed to open pre-order window.");
//            }
//        });
    }

    public void addTypeProductItem(int index) throws IOException {
        VBox p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ku/cs/view/productItem.fxml")));
        vBox.getChildren().add(p);
    }

    // ฟังก์ชันแสดงหน้าต่างสั่งจอง
    public void showPreOrderWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/preOrderWindow.fxml"));
        VBox preOrderLayout = loader.load();


        Stage preOrderStage = new Stage();
        preOrderStage.initModality(Modality.APPLICATION_MODAL); // ให้หน้าต่างนี้เด้งขึ้นมาด้านหน้า
        preOrderStage.initStyle(StageStyle.TRANSPARENT);

        // เพิ่มหน้าต่างลงใน scene
        Scene scene = new Scene(preOrderLayout);

        preOrderStage.setScene(scene);
        preOrderStage.showAndWait(); // รอให้หน้าต่างนี้ถูกปิดก่อนถึงจะดำเนินการต่อ
    }
}
