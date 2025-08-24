package com.nulp.gui.controllers;

import com.nulp.backend.models.Sweet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class SweetsScenesController extends Controller {
    @FXML
    protected Label searchQueryLabel;

    @FXML
    protected VBox sweetsContainer;

    @FXML
    protected Button sortButton;

    @FXML
    protected ChoiceBox sortChoice;

    @FXML
    protected Button backButton;

    @FXML
    public void initialize() throws Exception {
        super.initialize();
        setSortParameter();
    }

    protected ObservableList<String> getSortOptions() {
        Field[] fields = Sweet.class.getDeclaredFields();
        ObservableList<String> options = FXCollections.observableArrayList();

        for (Field field : fields) {
            options.add("by " + field.getName());
        }

        return options;
    }

    private void setSortParameter() {
        ObservableList<String> options = getSortOptions();
        sortChoice.setItems(options);

        sortButton.setOnAction(event -> {
            sortChoice.show();
        });

        sortChoice.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    sweetsContainer.getChildren().clear();
                    Comparator<Sweet> comparator = dataService.getComparators()
                            .get(options.indexOf(newValue));
                    sweets.sort(comparator);
                    sortChoice.setVisible(false);

                    try {
                        updateScene(sweets);
                    } catch (Exception e) {
                        logger.error("Unknown Error: " + e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                });
    }

    abstract protected void updateScene(List<Sweet> sweets) throws Exception;

    @FXML @Override
    protected void handleSweetSearchButton() throws Exception {
        String query = searchField.getText();

        if (query.isEmpty()) {
            return;
        }

        searchQueryLabel.setVisible(true);
        searchQueryLabel.setDisable(false);
        searchQueryLabel.setText("Результат пошуку \"" + query + "\"");

        backButton.setVisible(true);
        backButton.setDisable(false);

        List<Sweet> foundSweets = new ArrayList<>();
        query = query.toLowerCase();

        for (String keyword : query.split(" ")) {
            for (Sweet sweet : sweets) {

                if (sweet.getName().toLowerCase().contains(keyword)
                        || sweet.getSweetType().toLowerCase().contains(keyword)) {
                    foundSweets.add(sweet);
                }
            }
        }

        updateScene(foundSweets);
    }

    @FXML
    protected void handleBackButton() throws Exception{
        searchField.setText("");

        sweetsContainer.getChildren().clear();

        searchQueryLabel.setDisable(true);
        searchQueryLabel.setVisible(false);
        searchQueryLabel.setText("");

        backButton.setDisable(true);
        backButton.setVisible(false);

        updateScene(sweets);
    }
}
