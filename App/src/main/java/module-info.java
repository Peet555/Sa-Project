module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;


    opens ku.cs to javafx.fxml;
    exports ku.cs;

    opens ku.cs.appsales to javafx.fxml;
    exports ku.cs.appsales;

    opens ku.cs.controller to javafx.fxml;
    exports ku.cs.controller;
}