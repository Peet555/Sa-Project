package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class editConfirmWindowController {

    @FXML
    private Button okayButton ;



    @FXML
    public void okayClick(){
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }
}
