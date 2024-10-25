package ku.cs.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class recordOrderForSupWindowController {

    @FXML
    private Button confirmButton ;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            closeWindow();
        });
    }
    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }


}
