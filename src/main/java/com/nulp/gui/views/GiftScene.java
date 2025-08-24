package com.nulp.gui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GiftScene {
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/nulp/gui/views/fxml/GiftScene.fxml"));
        Parent root = loader.load();

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.initOwner(primaryStage);
        modalStage.setTitle("Gift");
        modalStage.setScene(new Scene(root));
        modalStage.setResizable(false);
        modalStage.showAndWait();
    }
}
