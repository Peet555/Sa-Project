package ku.cs.appsales;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.connect.DatabaseConnect;
import ku.cs.network.Client;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;

public class MainApplication extends Application {

    private static Stage stage;
    private static String currentStyle;
    final private static String THEME_PATH = "/ku/cs/theme/";
    final private static double MIN_WIGHT = 1200;
    final private static double MIN_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        MainApplication.stage = stage;
        stage.setResizable(true);
        stage.setMinWidth(MIN_WIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        configRoute();
        FXRouter.bind(this, stage);
        // เลือกเส้นทางที่ต้องการใช้เป็นค่าเริ่มต้น

        FXRouter.goTo("stock");

        setTheme("theme.css");
        //Client.init("localhost",(short) 25670);
        DatabaseConnect.initializeConnection();
    }

    private static void configRoute() {
        String resourcesPath = "ku/cs/view/";

        // กำหนดเส้นทางให้กับ FXRouter
        FXRouter.when("login", resourcesPath + "login.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("homePage", resourcesPath + "homePage.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("productModel", resourcesPath + "productModel.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("root", resourcesPath + "root.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("product", resourcesPath + "product.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("stock", resourcesPath + "stock.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("editStock", resourcesPath + "stockEditProduct.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("addStock", resourcesPath + "stockAddProduct.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("orderListPageCustomer", resourcesPath + "orderListCustomer.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("profile", resourcesPath + "profile.fxml", "SA Project", MIN_WIGHT, 760);

        FXRouter.when("verifyPayment", resourcesPath + "verifyPayment.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("checkProof", resourcesPath + "checkProofPayment.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("salerCheckOrder", resourcesPath + "salerCheckOrderPage.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("delivery", resourcesPath + "deliveryPrepare.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("orderStock", resourcesPath + "stockOrder.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("customerOrderList", resourcesPath + "orderListCustomer.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("orderDetailsPage", resourcesPath + "orderDetailsPage.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("customerOrderHistory", resourcesPath + "customerOrderHistory.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("employeeSellerProfile", resourcesPath + "employeeSellerProfile.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("employeeWarehouseProfile", resourcesPath + "employeeWarehouseProfile.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("ReCus", resourcesPath + "registerCustomer.fxml", "SA Project", MIN_WIGHT, 760);
        FXRouter.when("ReEm", resourcesPath + "registerEmployee.fxml", "SA Project", MIN_WIGHT, 760);


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
