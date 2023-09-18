package com.ricko;

import com.example.accountingofgoods.da.entity.HistoryBuy;
import com.example.accountingofgoods.dao.tables.HistoryBuyDAO;
import com.example.accountingofgoods.db.Connect;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HistoryBuyDAOTest {
    private static Connection connection = Connect.connect();
    private static HistoryBuyDAO historyBuyDAO;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";


    @BeforeAll
    public static void setUp() {

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            historyBuyDAO = new HistoryBuyDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        // Закриття підключення до бази даних
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void testAddHistoryBuy() {
        HistoryBuy historyBuy = new HistoryBuy(1, 1, LocalDateTime.now());
        assertTrue(historyBuyDAO.addHistoryBuy(historyBuy));
    }

    @Test
    @Order(2)
    public void testGetHistoryBuyById() {
        HistoryBuy historyBuy = historyBuyDAO.getHistoryBuyById(2);
        assertNotNull(historyBuy);
        assertEquals(2, historyBuy.getId());
    }

    @Test
    @Order(3)
    public void testGetAllHistoryBuys() {
        List<HistoryBuy> historyBuyList = historyBuyDAO.getAllHistoryBuys();
        assertNotNull(historyBuyList);
        assertFalse(historyBuyList.isEmpty());
    }

    @Test
    @Order(4)
    public void testUpdateHistoryBuy() {
        HistoryBuy historyBuy = historyBuyDAO.getHistoryBuyById(2);
        assertNotNull(historyBuy);

        historyBuy.setUserId(2);
        assertTrue(historyBuyDAO.updateHistoryBuy(historyBuy));

        HistoryBuy updatedHistoryBuy = historyBuyDAO.getHistoryBuyById(2);
        assertNotNull(updatedHistoryBuy);
        assertEquals(2, updatedHistoryBuy.getUserId());
    }

    @Test
    @Order(5)
    public void testDeleteHistoryBuy() {
        assertTrue(historyBuyDAO.deleteHistoryBuy(23));
        assertNull(historyBuyDAO.getHistoryBuyById(1));
    }
}
