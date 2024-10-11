package ku.cs.appsales;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {

    private static Stage stage;
    private static String currentStyle;
    final private static String THEME_PATH = "/ku/cs/theme/";
    final private static double MIN_WIGHT = 1200;
    final private static double MIN_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        stage.setResizable(true);
        stage.setMinWidth(MIN_WIGHT);
        stage.setMinHeight(MIN_HEIGHT);

        configRoute();

        FXRouter.bind(this, stage);
        FXRouter.goTo("login");
        setTheme("theme.css");
    }

    private static void configRoute() {
        String resourcesPath = "ku/cs/view/";
        FXRouter.when("login", resourcesPath + "login.fxml", "SA Project", MIN_WIGHT, 760);
    }

    /**
     * ใส่ theme ให้กับหน้าโปแกรม
     * @param styleFileName ไฟล์ theme ที่มีนามสกุลเป็น .css เช่น default.css
     */
    public static void setTheme(String styleFileName) {
        URL stylesheetURL = MainApplication.class.getResource(THEME_PATH + styleFileName);
        String styleSheet = "";

        if (stylesheetURL != null) styleSheet = stylesheetURL.toExternalForm(); // theme
        if (currentStyle != null) MainApplication.stage.getScene().getStylesheets().remove(currentStyle);
        MainApplication.stage.getScene().getStylesheets().add(styleSheet);
        MainApplication.currentStyle = styleSheet;
    }

    /**
     * เพิ่ม theme ให้กับหน้าโปรแกรม
     * @param styleFileName ไฟล์ theme ที่มีนามสกุลเป็น .css เช่น default.css
     */
    public static void addAddonStyle(String styleFileName) {
        URL stylesheetURL = MainApplication.class.getResource(THEME_PATH + styleFileName);
        String styleSheet = "";

        if (stylesheetURL != null) styleSheet = stylesheetURL.toExternalForm(); // theme
        MainApplication.stage.getScene().getStylesheets().add(styleSheet);
    }

    public static void clearAddonStyles() {
        MainApplication.stage.getScene().getStylesheets().removeIf((t)-> !t.equals(currentStyle));
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void stop() {

    }
}

