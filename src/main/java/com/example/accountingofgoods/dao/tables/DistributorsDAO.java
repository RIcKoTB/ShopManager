package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Distributors;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) для роботи з таблицею "distributors".
 */
public class DistributorsDAO {
    private final Connection connection;

    /**
     * Конструктор класу DistributorsDAO.
     *
     * @param connection Об'єкт Connection для взаємодії з базою даних.
     */
    public DistributorsDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Додає нового дистриб'ютора до бази даних.
     *
     * @param distributor Дистриб'ютор для додавання.
     * @return true, якщо додавання успішне, інакше false.
     */
    public boolean addDistributor(Distributors distributor) {
        String query = "INSERT INTO distributors (full_name, goods_id, company, logo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, distributor.getFull_name());
            statement.setInt(2, distributor.getGoodsID());
            statement.setString(3, distributor.getCompany());
            byte[] photoData = imageToByteArray(distributor.getLogo());
            statement.setBytes(4, photoData);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] imageToByteArray(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    private Image byteArrayToImage(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Отримує дистриб'ютора за його ідентифікатором.
     *
     * @param id Ідентифікатор дистриб'ютора.
     * @return Знайдений дистриб'ютор або null, якщо не знайдено.
     */
    public Distributors getDistributorById(int id) {
        String query = "SELECT * FROM distributors WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    int goodsID = resultSet.getInt("goods_id");
                    String company = resultSet.getString("company");
                    // Retrieve and convert the logo image from the database
                    byte[] logoData = resultSet.getBytes("logo");
                    Image logo = byteArrayToImage(logoData);

                    return new Distributors(id, fullName, goodsID, company, logo);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Отримує список всіх дистриб'юторів.
     *
     * @return Список дистриб'юторів.
     */
    public List<Distributors> getAllDistributors() {
        String query = "SELECT * FROM distributors";
        List<Distributors> distributorsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                int goodsID = resultSet.getInt("goods_id");
                String company = resultSet.getString("company");
                // Retrieve and convert the logo image from the database
                byte[] logoData = resultSet.getBytes("logo");
                Image logo = byteArrayToImage(logoData);

                Distributors distributor = new Distributors(id, fullName, goodsID, company, logo);
                distributorsList.add(distributor);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return distributorsList;
    }

    /**
     * Оновлює інформацію про дистриб'ютора.
     *
     * @param distributor Дистриб'ютор для оновлення.
     * @return true, якщо оновлення успішне, інакше false.
     */
    public boolean updateDistributor(Distributors distributor) {
        String query = "UPDATE distributors SET full_name = ?, goods_id = ?, company = ?, logo = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, distributor.getFull_name());
            statement.setInt(2, distributor.getGoodsID());
            statement.setString(3, distributor.getCompany());
            byte[] photoData = imageToByteArray(distributor.getLogo());
            statement.setBytes(4, photoData);
            statement.setInt(5, distributor.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє дистриб'ютора за його ідентифікатором.
     *
     * @param id Ідентифікатор дистриб'ютора.
     * @return true, якщо видалення успішне, інакше false.
     */
    public boolean deleteDistributor(int id) {
        String query = "DELETE FROM distributors WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
