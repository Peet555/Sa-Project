//package ku.cs.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.stage.Stage;
//
//public class preOrderProductDepositController {
//
//    @FXML
//    public void payDeposit(ActionEvent event) {
//        // โค้ดสำหรับการชำระค่ามัดจำ
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ดำเนินการชำระค่ามัดจำแล้ว", ButtonType.OK);
//        alert.showAndWait();
//    }
//
//    @FXML
//    public void goToProductModel(ActionEvent event) {
//        // โค้ดสำหรับการเปลี่ยนหน้าไปยัง productModelController
//        try {
//            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/ku/cs/productModel.fxml"));
//            javafx.scene.Parent root = loader.load();
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new javafx.scene.Scene(root));
//            stage.show();
//        } catch (Exception e) {
//            e.addSuppressed(new Throwable());
//
//        }
//    }
//}
