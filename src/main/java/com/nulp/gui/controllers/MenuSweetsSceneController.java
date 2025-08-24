package com.nulp.gui.controllers;

import com.nulp.backend.models.Category;
import com.nulp.backend.models.Sweet;
import com.nulp.gui.views.GiftScene;
import com.nulp.gui.views.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class MenuSweetsSceneController extends SweetsScenesController {
    @FXML
    protected Button giftButton;

    @FXML
    protected Label sweetsNum;

    @FXML
    protected Button homeButton;

    protected Timeline shakeTimeline;

    @FXML
    public void initialize() throws Exception {
        super.initialize();
        sweets = dataService.getAllSweets();
        sweetsNum.setText(Integer.toString(gift.getSweets().size()));
        giftButton.setOnMouseEntered(event -> startShakeAnimation(giftButton));
        giftButton.setOnMouseExited(event -> stopShakeAnimation(giftButton));
        giftButton.setOnAction(this::openGift);
        updateNumSweetsInGift();
        updateScene(sweets);
    }

    @Override
    protected void updateScene(List<Sweet> sweets) throws Exception {
        sweetsContainer.getChildren().clear();

        for (Sweet sweet : sweets) {
            VBox sweetIcon = new VBox();
            List<Field> fields = getAllFieldsClassOfSweet(sweet.getClass());

            ImageView sweetImage = getSweetImage(sweet);
            VBox.setMargin(sweetImage, new Insets(0, 0, 5, 0));

            //Відображення головної інформації про солодощі
            Label sweetName = new Label(sweet.getName());
            sweetName.setFont(Font.font("Montserrat", FontWeight.BOLD, 13));
            sweetName.setId("sweetName");

            sweetIcon.getChildren().addAll(sweetImage, sweetName);
            sweetIcon.setAlignment(Pos.CENTER);

            //Кнопка додавання солодощів
            Button addSweetButton = new Button("+");
            addSweetButton.setMinSize(40, 40);
            addSweetButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
            addSweetButton.getStyleClass().add("add-button");
            VBox.setMargin(addSweetButton, new Insets(0, 0, 10, 0));
            addSweetButton.setId("addSweetButton");
            addSweetButton.setOnAction(this::addSweetToGift);

            Tooltip tooltip = new Tooltip();
            tooltip.setFont(Font.font("Montserrat", 11));
            tooltip.setText("This button adds sweets to the gift");
            addSweetButton.setTooltip(tooltip);

            //Властивості солодощів
            GridPane sweetProperties = new GridPane();
            sweetProperties.setHgap(10);
            sweetProperties.setVgap(10);
            sweetProperties.setPadding(new Insets(20));
            sweetProperties.setAlignment(Pos.CENTER_LEFT);

            //Елемент для зміни кількості
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
            valueFactory.setValue(1);
            Spinner<Integer> numSweetsSpinner = new Spinner<>();
            numSweetsSpinner.setValueFactory(valueFactory);
            numSweetsSpinner.setPrefWidth(60);
            numSweetsSpinner.setEditable(true);
            numSweetsSpinner.setId("numSweetsSpinner");
            numSweetsSpinner.getStyleClass().add("custom-spinner");

            numSweetsSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    numSweetsSpinner.getEditor().setText(newValue.replaceAll("\\D", ""));
                }
            });

            // Додавання слухача для відслідковування змін значення Spinner
            numSweetsSpinner.valueProperty().addListener(new ChangeListener<>() {
                private boolean confirming = false;

                @Override
                public void changed(ObservableValue<? extends Integer> observable,
                                    Integer oldValue, Integer newValue) {
                    if (confirming) {
                        return;
                    }

                    if (newValue == null) {
                        confirming = true;
                        numSweetsSpinner.getValueFactory().setValue(oldValue);
                        confirming = false;
                    }
                }
            });

            //Контейнер для обєднання кнопки і спінера
            VBox addSweetsBox = new VBox();
            addSweetsBox.setAlignment(Pos.CENTER);
            addSweetsBox.getChildren().add(addSweetButton);
            addSweetsBox.getChildren().add(numSweetsSpinner);
            addSweetsBox.setId("addSweetsBox");

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                field.setAccessible(true);

                String name = field.getName();
                Object value = field.get(sweet);
                Label property = new Label(name + ": " + value);
                property.setFont(Font.font("Montserrat", 13));
                property.setId(field.getName());
                sweetProperties.add(property, i / 2, i % 2);
            }

            GridPane sweetContainer = new GridPane();
            sweetContainer.add(sweetIcon, 0, 0);
            sweetContainer.add(sweetProperties, 1, 0);
            sweetContainer.add(addSweetsBox, 2, 0);
            sweetContainer.setId("sweetContainer");

            // Додавання рядкових обмежень до кожного контейнера GridPane
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(100);
            rowConstraints.setMaxHeight(100);
            sweetContainer.getRowConstraints().addAll(rowConstraints);

            ColumnConstraints iconColumn = new ColumnConstraints();
            iconColumn.setMinWidth(150); // Мінімальна ширина колонки для іконки

            ColumnConstraints propertiesColumn = new ColumnConstraints();
            propertiesColumn.setHgrow(Priority.ALWAYS); // Встановлення автоматичної ширини за найбільш довгим елементом

            ColumnConstraints buttonColumn = new ColumnConstraints();
            buttonColumn.setMinWidth(60); // Мінімальна ширина колонки для кнопки

            sweetContainer.getColumnConstraints().addAll(iconColumn, propertiesColumn, buttonColumn);

            sweetsContainer.getChildren().add(sweetContainer);
        }
    }

    @FXML
    private void addSweetToGift(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
            VBox addSweetsBox = (VBox) clickedButton.getParent();
            GridPane sweetsContainers = (GridPane) addSweetsBox.getParent();
            Spinner<Integer> numSweetsSpinner = (Spinner<Integer>) addSweetsBox.lookup("#numSweetsSpinner");
            String sweetName = ((Label) sweetsContainers.lookup("#sweetName")).getText();

            gift.addSweet(dataService.findSweet(sweetName), numSweetsSpinner.getValue());
            updateNumSweetsInGift();
        } catch (Exception e) {
            logger.error("Unknown Error: " + e.getMessage(), e);
        }
    }

    public void showCategory(Category category) throws Exception {
        sweetsContainer.getChildren().clear();
        searchQueryLabel.setText(category.getName());
        sweets = category.getSweets();
        updateScene(sweets);
    }

    public void searchSweets(String query) throws Exception {
        searchField.setText(query);
        handleSweetSearchButton();
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
    private void openGift(ActionEvent event) {
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

    @FXML
    protected void returnToMainMenu() throws IOException {
        if (primaryStage == null) {
            Stage stage = (Stage) this.root.getScene().getWindow();
            new Main().start(stage);
        } else {
            new Main().start(primaryStage);
        }
    }
}
