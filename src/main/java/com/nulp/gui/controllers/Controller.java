package com.nulp.gui.controllers;

import com.nulp.backend.models.Gift;
import com.nulp.backend.models.Sweet;
import com.nulp.backend.services.DataService;
import com.nulp.backend.services.LoggerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller {
    protected static DataService dataService;

    protected static final Logger logger = LoggerService.getLogger();

    protected static final Gift gift = new Gift(new ArrayList<>());

    protected static List<Sweet> sweets;
    protected static Stage primaryStage;

    @FXML
    protected Button searchButton;

    @FXML
    protected TextField searchField;

    @FXML
    protected ScrollPane root;

    @FXML
    public void initialize() throws Exception {
        dataService = DataService.getInstance();
    }

    protected ImageView getSweetImage(Sweet sweet) {
        ImageView sweetImage = switch (sweet.getSweetType()) {
            case "Cookie" -> new javafx.scene.image.ImageView(new Image("file:src/main/resources/com/nulp/img/cookie_icon.png"));
            case "Cake" -> new javafx.scene.image.ImageView(new Image("file:src/main/resources/com/nulp/img/cake_icon.png"));
            case "Pastry" -> new javafx.scene.image.ImageView(new Image("file:src/main/resources/com/nulp/img/pastry_icon.png"));
            case "Chocolate" ->
                    new javafx.scene.image.ImageView(new Image("file:src/main/resources/com/nulp/img/chocolate_icon.png"));
            default -> new javafx.scene.image.ImageView(new Image("file:src/main/resources/com/nulp/img/candy_icon.png"));
        };

        sweetImage.setFitWidth(70);
        sweetImage.setFitHeight(70);
        sweetImage.setPreserveRatio(true);

        return sweetImage;
    }

    protected List<Field> getAllFieldsClassOfSweet(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals("name")) {
                    continue;
                }

                if (field.getName().equals("sweetType")) {
                    fields.addFirst(field);
                } else {
                    fields.add(field);
                }
            }

            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    @FXML
    abstract protected void handleSweetSearchButton() throws Exception;


}
