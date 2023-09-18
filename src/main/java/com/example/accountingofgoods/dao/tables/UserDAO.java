package com.example.accountingofgoods.dao.tables;

import com.example.accountingofgoods.da.entity.Session;
import com.example.accountingofgoods.da.entity.User;
import com.example.accountingofgoods.db.Connect;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection = Connect.connect();

    /**
     * Отримує всіх користувачів з таблиці "users".
     *
     * @return список об'єктів User
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT id, login, password, role_id FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                int role = resultSet.getInt("role_id");

                User user = new User(id, login, password, role);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getLastUser() {
        String SQL = """ 
                SELECT session.user_id
                FROM session
                WHERE session.data = (SELECT MAX(data) FROM `session`)
                """;
        try (
                PreparedStatement statement = Connect.connect().prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
        }
        return 0;
    }

    public int getRoleByUserId(int id) {
        String SQL = """ 
                SELECT users.role_id
                FROM users
                WHERE users.id = ?
                """;
        try (
                PreparedStatement statement = Connect.connect().prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("users.role_id");
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return 0;
    }

    public boolean singIn(String login, String password) {
        String SQL = """ 
                SELECT users.id,
                       users.login,
                       users.password
                FROM users
                WHERE users.login = ? 
                AND users.password = ?
                """;
        try (
                PreparedStatement statement = Connect.connect().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)){;
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                saveSession(new Session(
                        resultSet.getInt("id"),
                        LocalDateTime.now()
                ));
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void saveSession(Session session) {
        String SQL = """ 
                INSERT INTO session(user_id,data)
                VALUES (?, ?);
                """;
        try (
                PreparedStatement statement = Connect.connect().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, session.getUserId());
            statement.setTimestamp(2, Timestamp.valueOf(session.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("+");
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    /**
     * Отримує користувача за заданим ідентифікатором з таблиці "users".
     *
     * @param id ідентифікатор користувача
     * @return об'єкт User, якщо запис знайдено, null у протилежному випадку
     */
    public User getUserById(int id) {
        User user = null;

        String sql = "SELECT id, login, password, role_id FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    int role = resultSet.getInt("role_id");

                    user = new User(id, login, password, role);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Додає запис про користувача в таблицю "users".
     *
     * @param user об'єкт User, що представляє користувача
     * @return true, якщо запис про користувача успішно додано, false у протилежному випадку
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (login, password, role_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    user.setId(generatedId);
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Оновлює запис про користувача в таблиці "users".
     *
     * @param user об'єкт User, що містить оновлені дані користувача
     * @return true, якщо запис про користувача успішно оновлено, false у протилежному випадку
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET login = ?, password = ?, role_id = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole());
            statement.setInt(4, user.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Видаляє запис про користувача за заданим ідентифікатором з таблиці "users".
     *
     * @param id ідентифікатор користувача
     * @return true, якщо запис про користувача успішно видалено, false у протилежному випадку
     */
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
