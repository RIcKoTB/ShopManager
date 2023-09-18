package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.HistoryBuy;
import com.example.accountingofgoods.db.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що забезпечує доступ до таблиці "history_buy" бази даних.
 */
public class HistoryBuyDAO {
    private Connection connection = Connect.connect();

    /**
     * Конструктор класу HistoryBuyDAO.
     *
     * @param connection об'єкт Connection для взаємодії з базою даних
     */

    /**
     * Додає запис про купівлю до таблиці "history_buy".
     *
     * @param historyBuy об'єкт HistoryBuy, що представляє купівлю
     * @return true, якщо запис про купівлю успішно додано, false у протилежному випадку
     */
    public boolean addHistoryBuy(HistoryBuy historyBuy) {
        String query = "INSERT INTO history_buy (user_id, basket_id, data) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historyBuy.getUserId());
            statement.setInt(2, historyBuy.getBasket_id());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(historyBuy.getDate()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countTotalHistoryBuys() {
        String query = "SELECT COUNT(*) AS total FROM history_buy";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Отримує об'єкт купівлі з таблиці "history_buy" за заданим ідентифікатором.
     *
     * @param id ідентифікатор купівлі
     * @return об'єкт HistoryBuy, якщо знайдено, null у протилежному випадку
     */
    public HistoryBuy getHistoryBuyById(int id) {
        String query = "SELECT * FROM history_buy WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createHistoryBuyFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Отримує всі купівлі з таблиці "history_buy".
     *
     * @return список об'єктів HistoryBuy
     */
    public List<HistoryBuy> getAllHistoryBuys() {
        String query = "SELECT * FROM history_buy";
        List<HistoryBuy> historyBuyList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                historyBuyList.add(createHistoryBuyFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyBuyList;
    }

    /**
     * Оновлює існуючий запис про купівлю в таблиці "history_buy".
     *
     * @param historyBuy об'єкт HistoryBuy для оновлення
     * @return true, якщо запис про купівлю успішно оновлено, false у протилежному випадку
     */
    public boolean updateHistoryBuy(HistoryBuy historyBuy) {
        String query = "UPDATE history_buy SET user_id = ?, basket_id = ?, data = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historyBuy.getUserId());
            statement.setInt(2, historyBuy.getBasket_id());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(historyBuy.getDate()));
            statement.setInt(4, historyBuy.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє запис про купівлю з таблиці "history_buy" за заданим ідентифікатором.
     *
     * @param id ідентифікатор купівлі
     * @return true, якщо запис про купівлю успішно видалено, false у протилежному випадку
     */
    public boolean deleteHistoryBuy(int id) {
        String query = "DELETE FROM history_buy WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Створює об'єкт HistoryBuy на основі результату запиту ResultSet.
     *
     * @param resultSet результат запиту
     * @return об'єкт HistoryBuy
     * @throws SQLException якщо виникла помилка при отриманні даних з ResultSet
     */
    private HistoryBuy createHistoryBuyFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        int basketId = resultSet.getInt("basket_id");
        java.sql.Timestamp timestamp = resultSet.getTimestamp("data");
        java.time.LocalDateTime date = timestamp.toLocalDateTime();
        return new HistoryBuy(id, userId, basketId, date);
    }
}
