//package ku.cs.controller;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.stage.Stage;
//import javafx.scene.control.Button;
//import javafx.event.ActionEvent;
//
//import java.io.IOException;
//
//public class preOrderProductController {
//
//    @FXML
//    private Button cancelButton;
//
//    @FXML
//    private Button confirmButton;  // Corrected this line to mark it as @FXML
//
//    @FXML
//    public void closeWindow() {
//        // ปิดหน้าต่างสั่งจอง
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
//        stage.close();
//    }
//
//    @FXML
//    public void confirmOrder(ActionEvent event) {
//        try {
//            // โหลดไฟล์ FXML ของหน้า preOrderProductDeposit
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("preOrderWindowPayDeposit.fxml"));
//            javafx.scene.Parent root = loader.load();
//
//            // เปลี่ยนหน้าต่างไปเป็นหน้าจ่ายค่ามัดจำ
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new javafx.scene.Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            System.err.println();
//        }
//    }
//
//}
