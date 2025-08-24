package com.nulp.gui.controllers;

import com.nulp.backend.models.Category;
import com.nulp.gui.views.GiftScene;
import com.nulp.gui.views.SweetsScene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController extends Controller {
    @FXML
    protected Button giftButton;

    @FXML
    protected Label sweetsNum;

    @FXML
    protected Button homeButton;

    protected Timeline shakeTimeline;

    @FXML
    public void initialize() throws Exception{
        super.initialize();
        sweetsNum.setText(Integer.toString(gift.getSweets().size()));
        giftButton.setOnMouseEntered(event -> startShakeAnimation(giftButton));
        giftButton.setOnMouseExited(event -> stopShakeAnimation(giftButton));
        giftButton.setOnAction(this::handleGiftButton);
        updateNumSweetsInGift();
    }

    @FXML
    public void handleCategoryButton(ActionEvent event)
            throws NullPointerException {
        try {
            Button clickedButton = (Button) event.getSource();
            String categoryName = null;
            Category category;
            VBox parentVBox = (VBox) clickedButton.getParent();

            for (Node node : parentVBox.getChildren()) {
                if (node instanceof Label label) {
                    categoryName = label.getText();
                }
            }

            if (categoryName == null) {
                throw new NullPointerException();
            }

            category = dataService.findCategory(categoryName);

            if (category == null) {
                throw new NullPointerException();
            }
            new SweetsScene().start((Stage) root.getScene().getWindow(),
                    category);
        } catch (Exception e) {
            logger.error("Unknown Error: " + e.getMessage(), e);
        }
    }

    @FXML
    private void startShakeAnimation(Button button) {
        shakeTimeline = new Timeline(
                new KeyFrame(Duration.millis(0), e -> button.setRotate(-2)),
                new KeyFrame(Duration.millis(20), e -> button.setRotate(-4)),
                new KeyFrame(Duration.millis(40), e -> button.setRotate(-6)),
                new KeyFrame(Duration.millis(60), e -> button.setRotate(-8)),
                new KeyFrame(Duration.millis(80), e -> button.setRotate(-10)),
                new KeyFrame(Duration.millis(100), e -> button.setRotate(-8)),
                new KeyFrame(Duration.millis(120), e -> button.setRotate(-6)),
                new KeyFrame(Duration.millis(140), e -> button.setRotate(-4)),
                new KeyFrame(Duration.millis(160), e -> button.setRotate(-2)),
                new KeyFrame(Duration.millis(180), e -> button.setRotate(0)),
                new KeyFrame(Duration.millis(200), e -> button.setRotate(2)),
                new KeyFrame(Duration.millis(220), e -> button.setRotate(4)),
                new KeyFrame(Duration.millis(240), e -> button.setRotate(6)),
                new KeyFrame(Duration.millis(260), e -> button.setRotate(8)),
                new KeyFrame(Duration.millis(280), e -> button.setRotate(10)),
                new KeyFrame(Duration.millis(300), e -> button.setRotate(8)),
                new KeyFrame(Duration.millis(320), e -> button.setRotate(6)),
                new KeyFrame(Duration.millis(340), e -> button.setRotate(4)),
                new KeyFrame(Duration.millis(360), e -> button.setRotate(2)),
                new KeyFrame(Duration.millis(380), e -> button.setRotate(0))
        );
        shakeTimeline.setCycleCount(Timeline.INDEFINITE);
        shakeTimeline.play();
    }

    @FXML
    private void stopShakeAnimation(Button button) {
        if (shakeTimeline != null) {
            shakeTimeline.stop();
            button.setRotate(0);
        }
    }

    @FXML
    private void handleGiftButton(ActionEvent event) {
        try {
            primaryStage = (Stage) root.getScene().getWindow();
            new GiftScene().start(primaryStage);
            updateNumSweetsInGift();
        } catch (Exception e) {
            logger.error("Unknown Error: " + e.getMessage(), e);
        }
    }

    protected void updateNumSweetsInGift() {
        sweetsNum.setText(Integer.toString(gift.getSweets().size()));
    }

    @Override
    protected void handleSweetSearchButton() throws Exception {
        String request = searchField.getText();
        primaryStage = (Stage) root.getScene().getWindow();

        if (request.isEmpty()) {
            return;
        }

        new SweetsScene().start((Stage) root.getScene().getWindow(),
                request);
    }
}
