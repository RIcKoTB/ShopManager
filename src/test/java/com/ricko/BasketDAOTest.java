package com.ricko;

import com.example.accountingofgoods.da.entity.Basket;
import com.example.accountingofgoods.dao.tables.BasketDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasketDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";

    private static Connection connection;
    private BasketDAO basketDAO;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @BeforeEach
    public void createBasketDAO() {
        basketDAO = new BasketDAO();
    }

    @Test
    public void testAddBasket() {
        Basket basket = new Basket(1, 2, new ArrayList<>(), LocalDateTime.now());
        boolean result = basketDAO.addBasket(basket);

        assertTrue(result);
        assertNotNull(basket.getId());
    }
}