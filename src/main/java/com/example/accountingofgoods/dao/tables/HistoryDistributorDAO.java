package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.HistoryDistributor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що забезпечує доступ до таблиці "history_distributor" бази даних.
 */
public class HistoryDistributorDAO {
    private Connection connection;

    /**
     * Конструктор класу HistoryDistributorDAO.
     *
     * @param connection об'єкт Connection для взаємодії з базою даних
     */
    public HistoryDistributorDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Додає запис про дистриб'ютора до таблиці "history_distributor".
     *
     * @param historyDistributor об'єкт HistoryDistributor, що представляє дистриб'ютора
     * @return true, якщо запис про дистриб'ютора успішно додано, false у протилежному випадку
     */
    public boolean addHistoryDistributor(HistoryDistributor historyDistributor) {
        String query = "INSERT INTO history_distributor (id, distributor_id, department_id, data) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historyDistributor.getId());
            statement.setInt(2, historyDistributor.getDistributorId());
            statement.setInt(3, historyDistributor.getDepartmentId());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(historyDistributor.getDate()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Отримує об'єкт дистриб'ютора з таблиці "history_distributor" за заданим ідентифікатором.
     *
     * @param id ідентифікатор дистриб'ютора
     * @return об'єкт HistoryDistributor, якщо знайдено, null у протилежному випадку
     */
    public HistoryDistributor getHistoryDistributorById(int id) {
        String query = "SELECT * FROM history_distributor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createHistoryDistributorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Отримує всіх дистриб'юторів з таблиці "history_distributor".
     *
     * @return список об'єктів HistoryDistributor
     */
    public List<HistoryDistributor> getAllHistoryDistributors() {
        String query = "SELECT * FROM history_distributor";
        List<HistoryDistributor> historyDistributorList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                historyDistributorList.add(createHistoryDistributorFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyDistributorList;
    }

    /**
     * Оновлює запис про дистриб'ютора в таблиці "history_distributor".
     *
     * @param historyDistributor об'єкт HistoryDistributor, що представляє дистриб'ютора
     * @return true, якщо запис про дистриб'ютора успішно оновлено, false у протилежному випадку
     */
    public boolean updateHistoryDistributor(HistoryDistributor historyDistributor) {
        String query = "UPDATE history_distributor SET distributor_id = ?, department_id = ?, data = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historyDistributor.getDistributorId());
            statement.setInt(2, historyDistributor.getDepartmentId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(historyDistributor.getDate()));
            statement.setInt(4, historyDistributor.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє запис про дистриб'ютора з таблиці "history_distributor" за заданим ідентифікатором.
     *
     * @param id ідентифікатор дистриб'ютора
     * @return true, якщо запис про дистриб'ютора успішно видалено, false у протилежному випадку
     */
    public boolean deleteHistoryDistributor(int id) {
        String query = "DELETE FROM history_distributor WHERE id = ?";
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
     * Створює об'єкт HistoryDistributor на основі результату запиту ResultSet.
     *
     * @param resultSet результат запиту
     * @return об'єкт HistoryDistributor
     * @throws SQLException якщо виникла помилка при отриманні даних з ResultSet
     */
    private HistoryDistributor createHistoryDistributorFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int distributorId = resultSet.getInt("distributor_id");
        int departmentId = resultSet.getInt("department_id");
        java.sql.Timestamp timestamp = resultSet.getTimestamp("data");
        java.time.LocalDateTime date = timestamp.toLocalDateTime();
        return new HistoryDistributor(id, distributorId, departmentId, date);
    }
}
