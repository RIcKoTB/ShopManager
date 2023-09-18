package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Goods;
import com.example.accountingofgoods.db.Connect;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас DAO для взаємодії з таблицею "goods" (товари) бази даних.
 */
public class GoodsDAO {
    private Connection connection = Connect.connect();

    /**
     * Отримує всі товари з таблиці "goods".
     *
     * @return список об'єктів товарів
     */
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = new ArrayList<>();

        String sql = "SELECT id, name, photo, category_id, price FROM goods";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Blob photoData = resultSet.getBlob("photo");
                Image photo = convertBlobToImage(photoData);
                int categoryId = resultSet.getInt("category_id");
                double price = resultSet.getDouble("price");

                Goods goods = new Goods(id, name, photo, categoryId, price);
                goodsList.add(goods);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return goodsList;
    }

    /**
     * Отримує товар за заданим ідентифікатором з таблиці "goods".
     *
     * @param id ідентифікатор товару
     * @return об'єкт товару або null, якщо товар не знайдено
     */
    public Goods getGoodsById(int id) {
        Goods goods = null;

        String sql = "SELECT id, name, photo, category_id, price FROM goods WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    byte[] photoData = resultSet.getBytes("photo");
                    Image photo = byteArrayToImage(photoData);
                    int categoryId = resultSet.getInt("category_id");
                    double price = resultSet.getDouble("price");

                    goods = new Goods(id, name, photo, categoryId, price);
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return goods;
    }

    /**
     * Додає товар до таблиці "goods".
     *
     * @param goods об'єкт товару для додавання
     * @return true, якщо товар успішно додано, false у протилежному випадку
     */
    public boolean addGoods(Goods goods) {
        String sql = "INSERT INTO goods (name, photo, category_id, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goods.getName());

            // Convert image to byte array
            byte[] photoData = imageToByteArray(goods.getPhoto());

            // Set photoData as a BLOB parameter
            ByteArrayInputStream inputStream = new ByteArrayInputStream(photoData);
            statement.setBinaryStream(2, inputStream, photoData.length);

            statement.setInt(3, goods.getCategoryId());
            statement.setDouble(4, goods.getPrice());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getTotalGoodsCount() {
        String sql = "SELECT COUNT(*) AS total FROM goods";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public Image getGoodsImage(int id) {
        String sql = "SELECT photo FROM goods WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Blob photoData = resultSet.getBlob("photo");
                    return convertBlobToImage(photoData);
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Оновлює існуючий товар в таблиці "goods".
     *
     * @param goods об'єкт товару для оновлення
     * @return true, якщо товар успішно оновлено, false у протилежному випадку
     */
    public boolean updateGoods(Goods goods) {
        String sql = "UPDATE goods SET name = ?, photo = ?, category_id = ?, price = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goods.getName());
            byte[] photoData = imageToByteArray(goods.getPhoto());
            statement.setBytes(2, photoData);
            statement.setInt(3, goods.getCategoryId());
            statement.setDouble(4, goods.getPrice());
            statement.setInt(5, goods.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Видаляє товар з таблиці "goods" за заданим ідентифікатором.
     *
     * @param id ідентифікатор товару для видалення
     * @return true, якщо товар успішно видалено, false у протилежному випадку
     */
    public boolean deleteGoods(int id) {
        String sql = "DELETE FROM goods WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Конвертує об'єкт Image у масив байтів.
     *
     * @param image об'єкт Image для конвертації
     * @return масив байтів, що представляє зображення
     * @throws IOException якщо виникає помилка при конвертації
     */
    private byte[] imageToByteArray(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Визначення типу зображення
        String format = "jpg"; // тип за замовчуванням
        String[] imageFormats = ImageIO.getWriterFormatNames();
        for (String imageFormat : imageFormats) {
            if (ImageIO.getImageWritersByFormatName(imageFormat).hasNext()) {
                format = imageFormat;
                break;
            }
        }

        // Запис зображення у масив байтів
        ImageIO.write(bufferedImage, format, outputStream);

        return outputStream.toByteArray();
    }

    /**
     * Конвертує масив байтів у об'єкт Image.
     *
     * @param data масив байтів, що представляє зображення
     * @return об'єкт Image, що відтворює зображення
     * @throws IOException якщо виникає помилка при конвертації
     */
    private Image byteArrayToImage(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static Image convertBlobToImage(Blob blob) throws SQLException, IOException {
        if (blob == null) {
            return null;
        }

        try (InputStream inputStream = blob.getBinaryStream()) {
            return new Image(inputStream);
        }
    }
}
