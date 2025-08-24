package com.nulp.gui.views;

import com.nulp.backend.services.LoggerService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static {
        System.setProperty("mail.smtp.starttls.enable", "true");
        System.setProperty("mail.smtp.auth", "true");
        System.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.setProperty("log4j.configurationFile",
                "src/main/resources/log4j2.xml");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/com/nulp/gui/views/fxml/Menu.fxml"));
        primaryStage.setTitle("Sweet Dreams");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
                event.consume();
                exit(primaryStage);
        });
    }

    private void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            LoggerService.getLogger().error("Unknown Error: {}",
                    e.getMessage(), e);
        }
    }
}
