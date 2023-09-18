package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Category;
import com.example.accountingofgoods.db.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object (DAO) для роботи з таблицею "category".
 */
public class CategoryDAO {
    private static final String INSERT_CATEGORY = "INSERT INTO category (name) VALUES (?)";
    private static final String UPDATE_CATEGORY = "UPDATE category SET name = ? WHERE id = ?";
    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT id, name FROM category WHERE id = ?";
    private static final String SELECT_ALL_CATEGORIES = "SELECT id, name FROM category";

    private final Connection connection = Connect.connect();


    /**
     * Додає об'єкт Category до бази даних.
     *
     * @param category Об'єкт Category, який потрібно додати.
     * @throws SQLException Виникає, якщо сталася помилка при взаємодії з базою даних.
     */
    public void addCategory(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    category.setId(id);
                }
            }
        }
    }

    /**
     * Оновлює об'єкт Category в базі даних.
     *
     * @param category Об'єкт Category, який потрібно оновити.
     * @throws SQLException Виникає, якщо сталася помилка при взаємодії з базою даних.
     */
    public void updateCategory(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORY)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        }
    }
    /**
     * Видаляє об'єкт Category з бази даних за його ідентифікатором.
     *
     * @param categoryId Ідентифікатор Category, який потрібно видалити.
     * @throws SQLException Виникає, якщо сталася помилка при взаємодії з базою даних.
     */
    public void deleteCategory(int categoryId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        }
    }

    /**
     * Отримує об'єкт Category з бази даних за його ідентифікатором.
     *
     * @param categoryId Ідентифікатор Category, який потрібно отримати.
     * @return Об'єкт Category, якщо знайдено, або null, якщо не знайдено.
     * @throws SQLException Виникає, якщо сталася помилка при взаємодії з базою даних.
     */
    public Category getCategoryById(int categoryId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Category category = new Category(name);
                    category.setId(id);
                    return category;
                }
            }
        }
        return null;
    }

    /**
     * Отримує всі об'єкти Category з бази даних.
     *
     * @return Список об'єктів Category.
     * @throws SQLException Виникає, якщо сталася помилка при взаємодії з базою даних.
     */
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(name);
                category.setId(id);
                categories.add(category);
            }
        }
        return categories;
    }

    public List<Integer> getAllCategoryIds() throws SQLException {
        List<Integer> categoryIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                categoryIds.add(id);
            }
        }
        return categoryIds;
    }



}
