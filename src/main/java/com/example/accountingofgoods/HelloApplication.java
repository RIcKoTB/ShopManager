package com.example.accountingofgoods;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Головний клас для додатка Облік Товарів.
 * Цей клас розширює клас Application з JavaFX і відповідає за запуск додатка.
 */
public class HelloApplication extends Application {
    /**
     * Метод start додатку JavaFX.
     * Він завантажує файл FXML і створює сцену для вікна додатка.
     *
     * @param stage головне вікно додатка
     * @throws IOException якщо сталася помилка при завантаженні файлу FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Login");
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Точка входу в додаток.
     * Запускає додаток JavaFX, викликаючи метод `launch()`.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        launch();
    }
}