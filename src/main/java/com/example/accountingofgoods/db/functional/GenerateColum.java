package com.example.accountingofgoods.db.functional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
/**
 * Клас GenerateColumn містить методи для генерації компонентів для відображення колонок таблиці в графічному інтерфейсі.
 */
public class GenerateColum {
    /**
     * Генерує HBox для відображення текстової колонки таблиці з міткою та текстовим полем.
     *
     * @param lbl Мітка колонки.
     * @return HBox з міткою та текстовим полем.
     */
    public HBox generate(String lbl){
        HBox hBox = new HBox(50);
        hBox.setPadding(new Insets(0, 0, 10, 0));

        Label label = new Label(lbl);
        label.setFont(new Font("Tahoma", 14));
        label.setPrefWidth(70);

        TextField textField = new TextField();
        textField.setPrefWidth(172);
        textField.getStyleClass().add("textfield");

        hBox.getChildren().addAll(label, textField);
        return hBox;
    }

    /**
     * Генерує HBox для відображення колонки типу BLOB (зображення) таблиці з кнопкою для завантаження фото.
     *
     * @param lbl Мітка колонки.
     * @return HBox з зображенням та кнопкою для завантаження фото.
     */
    public HBox generateBlob(String lbl){
        HBox hBox = new HBox(30);
        hBox.setPadding(new Insets(10, 0, 0, 0));

        ImageView imageView = new ImageView();
        Button importButton = new Button();

        imageView.setFitHeight(80.0);
        imageView.setFitWidth(101.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        importButton.setMnemonicParsing(false);
        importButton.getStyleClass().add("import-btn");
        importButton.setText("Додати фото");

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Photo Uploader");

        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));

                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    imageView.setImage(image);
                }
            }
        });

        hBox.getChildren().addAll(imageView, importButton);
        return hBox;
    }
}
