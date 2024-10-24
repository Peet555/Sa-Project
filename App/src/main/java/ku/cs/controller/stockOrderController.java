package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class stockOrderController {

    @FXML
    public ImageView logo ;

    @FXML
    public TextField idStockOrder ;

    @FXML
    public TextField quantityStockOrder ;

    @FXML
    public void goStock(){
        try {
            FXRouter.goTo("stock");
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
