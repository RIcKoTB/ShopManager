package com.ricko;

import com.example.accountingofgoods.da.entity.User;
import com.example.accountingofgoods.dao.tables.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";


    private UserDAO userDao;
    private Connection connection;

    @Before
    public void setup() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        userDao = new UserDAO();
    }

    @After
    public void cleanup() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    public void testAddUser() {
        User newUser = new User(0, "newUser", "password123", 2);
        boolean addUserResult = userDao.addUser(newUser);
        assertTrue(addUserResult);
        assertTrue(newUser.getId() > 0);
    }

    @Test
    public void testUpdateUser() {
        int userId = 2;
        User userToUpdate = userDao.getUserById(userId);
        assertNotNull(userToUpdate);

        userToUpdate.setLogin("updatedUser");
        userToUpdate.setPassword("newPassword");
        userToUpdate.setRole(1);

        boolean updateUserResult = userDao.updateUser(userToUpdate);
        assertTrue(updateUserResult);
    }

    @Test
    public void testDeleteUser() {
        int userId = 3;
        boolean deleteUserResult = userDao.deleteUser(userId);
    }
}
