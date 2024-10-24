package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

//import java.lang.classfile.Label;

public class stockController {


    @FXML
    public TableView table ;

    @FXML
    public ImageView logo ;

    @FXML
    public void goOrder(){
        try {
            FXRouter.goTo("orderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goDeliver(){
        try {
            FXRouter.goTo("delivery");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
