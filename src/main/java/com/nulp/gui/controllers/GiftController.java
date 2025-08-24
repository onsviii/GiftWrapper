package com.nulp.gui.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulp.backend.models.Sweet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class GiftController extends SweetsScenesController {
    @FXML
    Button settingsButton;

    @FXML
    ContextMenu settingsMenu;

    @FXML
    MenuItem saveGift;

    @FXML
    MenuItem loadGift;


    @FXML
    public void initialize() throws Exception {
        super.initialize();
        sweets = gift.getUniqueSweets();
        updateScene(sweets);

        saveGift.setOnAction(event -> {
            if (gift.getSweets().isEmpty()) {
                emptyGiftAlert();
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Зберегти файл");

            FileChooser.ExtensionFilter extFilter = new FileChooser
                    .ExtensionFilter("Gift Files (*.gift)", "*.gift");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                saveGiftToFile(file);
            }
        });

        loadGift.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Вибереть файл для завантаження");
            fileChooser.getExtensionFilters().add(new FileChooser
                    .ExtensionFilter("Gift Files", "*.gift"));
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                try {
                    gift.setSweets(loadGiftFromFile(file));
                    sweets = gift.getUniqueSweets();
                    updateScene(sweets);
                } catch (Exception e) {
                    logger.error("Unknown Error: " + e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    protected void updateScene(List<Sweet> sweets) throws Exception {
        sweetsContainer.getChildren().clear();

        for (Sweet sweet : sweets) {
            VBox sweetIcon = new VBox();
            List<Field> fields = getAllFieldsClassOfSweet(sweet.getClass());

            ImageView sweetImage = getSweetImage(sweet);
            VBox.setMargin(sweetImage, new Insets(0, 0, 5, 0));

            // Відображення головної інформації про солодощі
            Label sweetName = new Label(sweet.getName());
            sweetName.setFont(Font.font("Montserrat", FontWeight.BOLD, 13));
            sweetName.setId("sweetName");

            sweetIcon.getChildren().addAll(sweetImage, sweetName);
            sweetIcon.setAlignment(Pos.CENTER);

            // Властивості солодощів
            GridPane sweetProperties = new GridPane();
            sweetProperties.setHgap(10);
            sweetProperties.setVgap(10);
            sweetProperties.setPadding(new Insets(20));
            sweetProperties.setAlignment(Pos.CENTER_LEFT);

            // Елемент для зміни кількості
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50000);
            valueFactory.setValue(gift.getSweetQuantity(sweet));
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
                    } else if (newValue == 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Deletion");
                        alert.setHeaderText("Confirm Deletion");
                        alert.setContentText("Are you sure you want to delete this item?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            gift.deleteSweet(sweet, oldValue);
                            deleteSweetRecord(numSweetsSpinner);
                        } else {
                            confirming = true;
                            numSweetsSpinner.getValueFactory().setValue(oldValue);
                            confirming = false;
                        }
                    } else if (newValue > oldValue) {
                        gift.addSweet(sweet, newValue - oldValue);
                    } else if (newValue < oldValue) {
                        gift.deleteSweet(sweet, oldValue - newValue);
                    }
                }
            });

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
            sweetContainer.add(numSweetsSpinner, 2, 0);
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

    private void deleteSweetRecord(Node nodeToDelete) {
        int rowIndexToRemove = GridPane.getRowIndex(nodeToDelete);
        GridPane gridPane = (GridPane) nodeToDelete.getParent();

        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowIndexToRemove);
        gridPane.getRowConstraints().remove(rowIndexToRemove);

        // Перемістити всі рядки нижче видаленого на один вгору
        for (int i = rowIndexToRemove + 1; i < gridPane.getRowCount(); i++) {
            for (Node child : gridPane.getChildren()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if (rowIndex != null && rowIndex == i) {
                    GridPane.setRowIndex(child, i - 1);
                }
            }
        }
    }

    private List<Sweet> loadGiftFromFile(File file) throws IOException{
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, new TypeReference<>() {});
    }

    private void saveGiftToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<>() {}).writeValue(file,
                    gift.getSweets());
        } catch (IOException e) {
            logger.error("Unknown Error: " + e.getMessage(), e);
        }
    }

    private void emptyGiftAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The Gift Is Empty");
        alert.setContentText("You cannot save an empty gift to a file.");
        alert.showAndWait();
    }

    @FXML
    private void openSettingsMenu() {
        settingsButton.setOnMousePressed(event -> {
            settingsMenu.show(settingsButton, event.getScreenX(),
                    event.getScreenY());
            event.consume();
        });
    }
}
