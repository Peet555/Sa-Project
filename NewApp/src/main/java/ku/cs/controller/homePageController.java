package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class homePageController {

    public GridPane gridPane;
    private HBox currentSelectedProduct = null; // ใช้เพื่อเก็บ item ที่ถูกเลือก

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;

    @FXML
    public void initialize() throws IOException {
        // จำนวนสินค้า
        int itemCount = 6;
        // จำนวนคอลัมน์ใน gridPane
        int columns = 3;

        String[] typeNames = {"grill","iron","kettle","oil-free-fryer","rice-cooker","washing-machine"};
        String[] imagePaths = {
                "/ku/cs/picture/grill.jpg",
                "/ku/cs/picture/iron.jpg",
                "/ku/cs/picture/kettle.jpg",
                "/ku/cs/picture/oil-free-fryer.jpg",
                "/ku/cs/picture/rice-cooker.jpg",
                "/ku/cs/picture/washing-machine.jpg",
        };


        for (int i = 0; i < itemCount; i++) {
            addTypeProductItem(i, columns, typeNames[i], imagePaths[i]);
        }
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

    public void addTypeProductItem(int index, int columns, String typeName, String imagePath) throws IOException {

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ku/cs/view/typeProductItem.fxml")));
        HBox p = loader.load();

        typeProductItemController controller = loader.getController();
        controller.typeName.setText(typeName);
        controller.imageTypeProduct.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));

        // กำหนดจำนวนแถว
        int row = index / columns;  // 4 คอลัมน์
        int col = (index % columns) + 1;  // 4 คอลัมน์

        // เมื่อคลิกครั้งแรกให้เปลี่ยนสีเป็นฟ้า
        p.setOnMousePressed(event -> {
            // หากมี item ที่เลือกอยู่แล้ว ให้เอาสีออก
            if (currentSelectedProduct != null) {
                currentSelectedProduct.setStyle("");  // ลบสีที่เลือก
            }

            // เปลี่ยนสีของ item ที่ถูกคลิก
            p.setStyle("-fx-background-color: lightblue;");
            currentSelectedProduct = p;  // เก็บ item ที่ถูกเลือก
        });

        // เมื่อ Double Click ที่ item จะเปลี่ยนไปหน้า ProductModel
        p.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("Double clicked on item at row " + row + ", column " + col);

                // เปลี่ยนไปที่หน้า ProductModel
                try {
                    FXRouter.goTo("productModel");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า ProductModel ไม่ได้");

                }
            }
        });

        // เพิ่ม item ลงใน gridPane ที่ตำแหน่ง row, col
        gridPane.add(p, col, row);
    }
}
