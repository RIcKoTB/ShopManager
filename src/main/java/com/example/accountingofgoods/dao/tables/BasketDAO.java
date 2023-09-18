package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Basket;
import com.example.accountingofgoods.db.Connect;
import com.example.accountingofgoods.da.entity.Goods;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) для роботи з таблицею "baskets".
 */
public class BasketDAO {
    private Connection connection = Connect.connect();


    /**
     * Додає об'єкт Basket до бази даних.
     *
     * @param basket Об'єкт Basket, який потрібно додати.
     * @return Повертає true, якщо Basket успішно додано, або false у протилежному випадку.
     */
    public boolean addBasket(Basket basket) {
        String query = "INSERT INTO basket (user_id, goods_list, data_create) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, basket.getUserId());

            // Конвертувати список об'єктів Goods у список рядків
            List<String> goodsList = basket.getGoodsList().stream()
                    .map(Goods::getName)  // Замініть `toString()` на відповідний метод для отримання рядка з об'єкту Goods
                    .collect(Collectors.toList());

            String goodsListString = String.join(",", goodsList);
            statement.setString(2, goodsListString);

            statement.setTimestamp(3, Timestamp.valueOf(basket.getDate()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    basket.setId(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }



    /**
     * Оновлює об'єкт Basket в базі даних.
     *
     * @param basket Об'єкт Basket, який потрібно оновити.
     * @return Повертає true, якщо Basket успішно оновлено, або false у протилежному випадку.
     */
    public boolean updateBasket(Basket basket) {
        String query = "UPDATE basket SET user_id = ?, goods_list = ?, data_create = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, basket.getUserId());
            statement.setArray(2, connection.createArrayOf("varchar", basket.getGoodsList().toArray()));
            statement.setTimestamp(3, Timestamp.valueOf(basket.getDate()));
            statement.setInt(4, basket.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє об'єкт Basket з бази даних за його ідентифікатором.
     *
     * @param id Ідентифікатор Basket.
     * @return Повертає true, якщо Basket успішно видалено, або false у протилежному випадку.
     */
    public boolean deleteBasket(int id) {
        String query = "DELETE FROM basket WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
