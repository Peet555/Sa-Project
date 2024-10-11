package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class homePageController {

    public VBox vBox;

    @FXML
    public void initialize() throws IOException {
        addTypeProductItem();
    }


    public void addTypeProductItem() throws IOException {
        typeProductItemController typeProductItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ku/cs/typeProductItem.fxml")));
    }
}
