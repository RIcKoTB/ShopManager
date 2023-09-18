package com.example.accountingofgoods.db.functional;

import com.example.accountingofgoods.db.Connect;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас Request містить методи для виконання запитів до бази даних.
 */
public class Request {
    /**
     * Вставляє новий рядок в таблицю з вказаними значеннями полів.
     *
     * @param tableName     Назва таблиці.
     * @param fieldValues   Значення полів.
     */
    public void insert(String tableName, List<Object> fieldValues) throws SQLException, IOException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append(" VALUES (");
        for (int i = 0; i < fieldValues.size(); i++) {
            sql.append("?");
            if (i < fieldValues.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        //try (Connection connection = Connect.connect();
             PreparedStatement statement = Connect.connect().prepareStatement(sql.toString());
            for (int i = 0; i < fieldValues.size(); i++) {
                Object fieldValue = fieldValues.get(i);
                if (fieldValue instanceof Image) {
                    // Конвертувати Image в масив байтів і встановити його як BLOB
                    Image image = (Image) fieldValue;
                    byte[] imageData = convertImageToByteArray(image);
                    statement.setBytes(i + 1, imageData);
                } else {
                    statement.setObject(i + 1, fieldValue);
                }
            }

            statement.executeUpdate();
        //} catch (SQLException | IOException e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Помилка!");
//            alert.setHeaderText("З чим це пов'язано");
//            alert.setContentText("Дані введено некоректно, перевірте існування зовнішніх ключів");
//            alert.showAndWait();
       // }
    }

    private byte[] convertImageToByteArray(Image image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Оновлює рядок в таблиці з вказаними значеннями полів та умовою.
     *
     * @param tableName     Назва таблиці.
     * @param fieldValues   Значення полів.
     * @param labelList     Список міток полів.
     */
    public void update(String tableName, List<Object> fieldValues, List<Object> labelList) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName).append(" SET ");

        for (int i = 0; i < labelList.size(); i++) {
            sql.append(labelList.get(i)).append(" = ?");
            if (i < labelList.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE " + labelList.get(0) + " = " + fieldValues.get(0));

        try (Connection connection = Connect.connect();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < labelList.size(); i++) {
                statement.setObject(i + 1, fieldValues.get(i));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка!");
            alert.setHeaderText("З чим це пов'язано");
            alert.setContentText("Дані введено некоректно, перевірте існування зовнішніх ключів");
            alert.showAndWait();
        }
    }

    /**
     * Видаляє рядок з таблиці за вказаним ідентифікатором.
     *
     * @param tableName     Назва таблиці.
     * @param idValue       Значення ідентифікатора.
     */
    public void delete(String tableName, String idValue) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection connection = Connect.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Виконує пошук рядків у таблиці за вказаним полем та значенням.
     *
     * @param tableName     Назва таблиці.
     * @param columnName    Назва колонки.
     * @param searchValue   Значення для пошуку.
     * @param labelList     Список міток полів.
     * @return Список результатів пошуку.
     */
    public List<String> search(String tableName, String columnName, String searchValue, List<Object> labelList) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT * FROM ").append(tableName).append(" WHERE ");
        sql.append(columnName).append(" LIKE ?");

        List<String> resultList = new ArrayList<>();

        try (Connection connection = Connect.connect();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            statement.setString(1, "%" + searchValue + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder rowData = new StringBuilder();

                for (int i = 0; i < labelList.size(); i++) {
                    String columnValue = resultSet.getString(labelList.get(i).toString());
                    rowData.append(columnValue).append(" ");
                }

                resultList.add(rowData.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String row : resultList) {
            System.out.println(row);
        }
        return resultList;
    }

    /**
     * Оновлює інформацію про товар за заданим ідентифікатором.
     *
     * @param id            Значення ідентифікатора товару.
     * @param name          Назва товару.
     * @param photo         Фото товару.
     * @param categoryId    Ідентифікатор категорії товару.
     * @param price         Ціна товару.
     */
    public void updateGoods(String id, String name, String photo, String categoryId, String price){
        String query = "UPDATE goods SET name = ?, photo = ?, category_id = ?, price = ? WHERE id = ?";

        try (Connection connection = Connect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(5, id);
            statement.setString(1, name);
            statement.setString(2, photo);
            statement.setString(3, categoryId);
            statement.setString(4, price);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
