package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Departments;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) для роботи з таблицею "departments".
 */
public class DepartmentsDAO {
    private Connection connection;

    /**
     * Конструктор класу DepartmentsDAO.
     *
     * @param connection Об'єкт Connection для взаємодії з базою даних.
     */
    public DepartmentsDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Додає новий відділ до таблиці "departments".
     *
     * @param departments відділ для додавання
     * @return true, якщо відділ успішно додано, false у протилежному випадку
     */
    public boolean addDepartments(Departments departments) {
        // SQL запит для додавання нового відділу до таблиці
        String query = "INSERT INTO departments (city, street, photo) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, departments.getCity());
            statement.setString(2, departments.getStreet());
            // Припускаючи, що фото зберігається як BLOB в базі даних
            statement.setBytes(3, convertImageToByteArray(departments.getPhoto()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Отримує відділ з таблиці "departments" за ідентифікатором.
     *
     * @param id ідентифікатор відділу
     * @return об'єкт відділу, якщо відділ знайдено, null у протилежному випадку
     */
    public Departments getDepartmentsById(int id) {
        String query = "SELECT * FROM departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createDepartmentsFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Отримує всі відділи з таблиці "departments".
     *
     * @return список об'єктів відділів
     */
    public List<Departments> getAllDepartments() {
        String query = "SELECT * FROM departments";
        List<Departments> departmentsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                departmentsList.add(createDepartmentsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentsList;
    }

    /**
     * Оновлює відділ в таблиці "departments".
     *
     * @param departments оновлений об'єкт відділу
     * @return true, якщо відділ успішно оновлено, false у протилежному випадку
     */
    public boolean updateDepartments(Departments departments) {
        String query = "UPDATE departments SET city = ?, street = ?, photo = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, departments.getCity());
            statement.setString(2, departments.getStreet());
            // Припускаючи, що фото зберігається як BLOB в базі даних
            statement.setBytes(3, convertImageToByteArray(departments.getPhoto()));
            statement.setInt(4, departments.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє відділ з таблиці "departments" за ідентифікатором.
     *
     * @param id ідентифікатор відділу для видалення
     * @return true, якщо відділ успішно видалено, false у протилежному випадку
     */
    public boolean deleteDepartments(int id) {
        String query = "DELETE FROM departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Departments createDepartmentsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        // Припускаючи, що фото зберігається як BLOB в базі даних
        byte[] photoData = resultSet.getBytes("photo");
        Image photo = convertByteArrayToImage(photoData);
        return new Departments(id, city, street, photo);
    }

    private byte[] convertImageToByteArray(Image photo) {
        // Реалізуйте логіку конвертації на основі вашого специфічного вимогам
        // Це просто пуста реалізація-заглушка
        return new byte[0];
    }

    private Image convertByteArrayToImage(byte[] data) {
        // Реалізуйте логіку конвертації на основі вашого специфічного вимогам
        // Це просто пуста реалізація-заглушка
        return null;
    }
}
