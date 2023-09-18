package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO {
    private Connection connection;

    public StoreDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Додає запис про склад в таблицю "store".
     *
     * @param store об'єкт Store, що представляє склад
     * @return true, якщо запис про склад успішно додано, false у протилежному випадку
     */
    public boolean addStore(Store store) {
        String query = "INSERT INTO store (id, name, good_id, count, department_id, distributor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, store.getId());
            statement.setString(2, store.getName());
            statement.setInt(3, store.getGoodsId());
            statement.setInt(4, store.getCount());
            statement.setInt(5, store.getDepartmentId());
            statement.setInt(6, store.getDistributorId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Отримує запис про склад за заданим ідентифікатором з таблиці "store".
     *
     * @param id ідентифікатор складу
     * @return об'єкт Store, якщо запис знайдено, null у протилежному випадку
     */
    public Store getStoreById(int id) {
        String query = "SELECT * FROM store WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createStoreFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Отримує всі записи про склади з таблиці "store".
     *
     * @return список об'єктів Store
     */
    public List<Store> getAllStores() {
        String query = "SELECT * FROM store";
        List<Store> storeList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                storeList.add(createStoreFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeList;
    }

    /**
     * Оновлює запис про склад в таблиці "store".
     *
     * @param store об'єкт Store, що представляє склад
     * @return true, якщо запис про склад успішно оновлено, false у протилежному випадку
     */
    public boolean updateStore(Store store) {
        String query = "UPDATE store SET name = ?, good_id = ?, count = ?, department_id = ?, distributor_id = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, store.getName());
            statement.setInt(2, store.getGoodsId());
            statement.setInt(3, store.getCount());
            statement.setInt(4, store.getDepartmentId());
            statement.setInt(5, store.getDistributorId());
            statement.setInt(6, store.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Видаляє запис про склад за заданим ідентифікатором з таблиці "store".
     *
     * @param id ідентифікатор складу
     * @return true, якщо запис про склад успішно видалено, false у протилежному випадку
     */
    public boolean deleteStore(int id) {
        String query = "DELETE FROM store WHERE id = ?";
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
     * Створює об'єкт Store на основі результатів запиту ResultSet.
     *
     * @param resultSet результати запиту до таблиці "store"
     * @return об'єкт Store
     * @throws SQLException якщо виникла помилка при отриманні даних з ResultSet
     */
    private Store createStoreFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int goodsId = resultSet.getInt("good_id");
        int count = resultSet.getInt("count");
        int departmentId = resultSet.getInt("department_id");
        int distributorId = resultSet.getInt("distributor_id");
        return new Store(id, name, goodsId, count, departmentId, distributorId);
    }
}
