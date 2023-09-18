package com.ricko;

import com.example.accountingofgoods.da.entity.HistoryDistributor;
import com.example.accountingofgoods.dao.tables.HistoryDistributorDAO;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HistoryDistributorDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";


    private static Connection connection;
    private static HistoryDistributorDAO historyDistributorDAO;

    @BeforeAll
    static void setup() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            historyDistributorDAO = new HistoryDistributorDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void cleanup() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void resetDatabase() {
        // Виконати дії для очищення та підготовки бази даних перед кожним тестом
        // Наприклад, можна виконати SQL запити для створення таблиці або видалити існуючі дані
        // перед тестуванням кожного методу DAO
    }

    @Test
    void addHistoryDistributor_ShouldReturnTrueWhenAddedSuccessfully() {
        HistoryDistributor historyDistributor = new HistoryDistributor(1, 1, 1, LocalDateTime.now());

        boolean result = historyDistributorDAO.addHistoryDistributor(historyDistributor);

        assertTrue(result);
    }

    @Test
    void getHistoryDistributorById_ShouldReturnCorrectHistoryDistributor() {
        // Перед тестом додайте потрібні дані в базу даних
        int id = 1;

        HistoryDistributor expected = new HistoryDistributor(id, 1, 1, LocalDateTime.now());

        HistoryDistributor actual = historyDistributorDAO.getHistoryDistributorById(id);

        assertEquals(expected, actual);
    }

    @Test
    void getAllHistoryDistributors_ShouldReturnNonEmptyList() {
        // Перед тестом додайте потрібні дані в базу даних

        List<HistoryDistributor> historyDistributors = historyDistributorDAO.getAllHistoryDistributors();

        assertFalse(historyDistributors.isEmpty());
    }

    @Test
    void updateHistoryDistributor_ShouldReturnTrueWhenUpdatedSuccessfully() {
        // Перед тестом додайте потрібні дані в базу даних
        HistoryDistributor historyDistributor = new HistoryDistributor(1, 1, 1, LocalDateTime.now());

        boolean result = historyDistributorDAO.updateHistoryDistributor(historyDistributor);

        assertTrue(result);
    }

    @Test
    void deleteHistoryDistributor_ShouldReturnTrueWhenDeletedSuccessfully() {
        // Перед тестом додайте потрібні дані в базу даних
        int id = 1;

        boolean result = historyDistributorDAO.deleteHistoryDistributor(id);

        assertTrue(result);
    }
}
