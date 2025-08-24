package com.nulp.gui.views;

import com.nulp.backend.models.Category;
import com.nulp.gui.controllers.MenuSweetsSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SweetsScene{

    public void start(Stage stage, Category category) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/nulp/gui/views/fxml/SweetsScene.fxml"));
        Parent root = loader.load();
        MenuSweetsSceneController controller = loader.getController();
        controller.showCategory(category);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void start(Stage stage, String request) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/nulp/gui/views/fxml/SweetsScene.fxml"));
        Parent root = loader.load(); // Завантаження FXML
        MenuSweetsSceneController controller = loader.getController();

        controller.searchSweets(request);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
