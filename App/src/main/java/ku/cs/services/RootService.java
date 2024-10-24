package ku.cs.services;

import javafx.util.Duration;
import ku.cs.controller.rootController;

public class RootService {
    private static rootController controller;

    public static rootController getController() {
        return controller;
    }

    public static void setController(rootController controller) {
        RootService.controller = controller;
    }

    public static void open(String path) {
        controller.open(path);
    }


    public static void showBar(String text) {
        showBar(text, Duration.seconds(3));
    }

    public static void showBar(String text, Duration duration) {
        showBar(text, rootController.Color.GREEN, duration);
    }

    public static void showBar(String text, rootController.Color color, Duration duration) {
        controller.showBar(text, color, duration);
    }

    public static void showErrorBar(String text) {
        showErrorBar(text, Duration.seconds(3));
    }

    public static void showErrorBar(String text, Duration duration) {
        showBar(text, rootController.Color.RED, duration);
    }

    public static void showLoadingIndicator() {
        controller.loadingPane.setVisible(true);
    }

    public static void hideLoadingIndicator() {
        controller.loadingPane.setVisible(false);
    }

    public static Data getData() {
        return controller.getData();
    }
}
