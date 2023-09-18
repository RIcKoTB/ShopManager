package com.ricko;

import com.example.accountingofgoods.da.entity.Store;
import com.example.accountingofgoods.dao.tables.StoreDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";


    private Connection connection;
    private StoreDAO storeDAO;

    @BeforeAll
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        storeDAO = new StoreDAO(connection);
    }

    @AfterAll
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @BeforeEach
    void clearData() {
        // Видалення всіх записів з таблиці store перед кожним тестом
        String query = "DELETE FROM store";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddStore() {
        // Створення нового об'єкту Store
        Store store = new Store(1, "Product A", 1, 10, 1, 1);

        // Додавання об'єкту Store до бази даних
        assertTrue(storeDAO.addStore(store));

        // Перевірка, чи було збережено правильні дані
        Store retrievedStore = storeDAO.getStoreById(store.getId());
        assertNotNull(retrievedStore);
        assertEquals(store.getId(), retrievedStore.getId());
        assertEquals(store.getName(), retrievedStore.getName());
        assertEquals(store.getGoodsId(), retrievedStore.getGoodsId());
        assertEquals(store.getCount(), retrievedStore.getCount());
        assertEquals(store.getDepartmentId(), retrievedStore.getDepartmentId());
        assertEquals(store.getDistributorId(), retrievedStore.getDistributorId());
    }

    @Test
    void testGetStoreById() {
        // Додавання декількох записів до бази даних
        Store store1 = new Store(1, "Product A", 1, 10, 1, 1);
        Store store2 = new Store(2, "Product B", 1, 5, 2, 1);
        storeDAO.addStore(store1);
        storeDAO.addStore(store2);

        // Отримання запису з бази даних за ідентифікатором
        Store retrievedStore = storeDAO.getStoreById(store1.getId());
        assertNotNull(retrievedStore);
        assertEquals(store1.getId(), retrievedStore.getId());
        assertEquals(store1.getName(), retrievedStore.getName());
        assertEquals(store1.getGoodsId(), retrievedStore.getGoodsId());
        assertEquals(store1.getCount(), retrievedStore.getCount());
        assertEquals(store1.getDepartmentId(), retrievedStore.getDepartmentId());
        assertEquals(store1.getDistributorId(), retrievedStore.getDistributorId());

        // Перевірка, що правильний запис не змінюється після отримання іншого запису
        retrievedStore = storeDAO.getStoreById(store2.getId());
        assertNotNull(retrievedStore);
        assertEquals(store2.getId(), retrievedStore.getId());
        assertEquals(store2.getName(), retrievedStore.getName());
        assertEquals(store2.getGoodsId(), retrievedStore.getGoodsId());
        assertEquals(store2.getCount(), retrievedStore.getCount());
        assertEquals(store2.getDepartmentId(), retrievedStore.getDepartmentId());
        assertEquals(store2.getDistributorId(), retrievedStore.getDistributorId());

        // Перевірка, що невірний ідентифікатор повертає null
        assertNull(storeDAO.getStoreById(9999));
    }

    @Test
    void testGetAllStores() {
        // Додавання декількох записів до бази даних
        Store store1 = new Store(1, "Product A", 1, 10, 1, 1);
        Store store2 = new Store(2, "Product B", 1, 5, 2, 1);
        storeDAO.addStore(store1);
        storeDAO.addStore(store2);

        // Отримання всіх записів з бази даних
        List<Store> storeList = storeDAO.getAllStores();
        assertEquals(2, storeList.size());

        // Перевірка, чи повернуті правильні записи
        Store retrievedStore1 = storeList.get(0);
        assertNotNull(retrievedStore1);
        assertEquals(store1.getId(), retrievedStore1.getId());
        assertEquals(store1.getName(), retrievedStore1.getName());
        assertEquals(store1.getGoodsId(), retrievedStore1.getGoodsId());
        assertEquals(store1.getCount(), retrievedStore1.getCount());
        assertEquals(store1.getDepartmentId(), retrievedStore1.getDepartmentId());
        assertEquals(store1.getDistributorId(), retrievedStore1.getDistributorId());

        Store retrievedStore2 = storeList.get(1);
        assertNotNull(retrievedStore2);
        assertEquals(store2.getId(), retrievedStore2.getId());
        assertEquals(store2.getName(), retrievedStore2.getName());
        assertEquals(store2.getGoodsId(), retrievedStore2.getGoodsId());
        assertEquals(store2.getCount(), retrievedStore2.getCount());
        assertEquals(store2.getDepartmentId(), retrievedStore2.getDepartmentId());
        assertEquals(store2.getDistributorId(), retrievedStore2.getDistributorId());
    }

    @Test
    void testUpdateStore() {
        // Створення початкового запису
        Store store = new Store(1, "Product A", 2, 10, 1, 1);
        storeDAO.addStore(store);

        // Оновлення запису
        store.setName("New Product A");
        store.setCount(5);
        assertTrue(storeDAO.updateStore(store));

        // Перевірка, чи були оновлені дані
        Store retrievedStore = storeDAO.getStoreById(store.getId());
        assertNotNull(retrievedStore);
        assertEquals(store.getId(), retrievedStore.getId());
        assertEquals(store.getName(), retrievedStore.getName());
        assertEquals(store.getCount(), retrievedStore.getCount());
    }

    @Test
    void testDeleteStore() {
        // Додавання запису до бази даних
        Store store = new Store(1, "Product A", 1, 10, 1, 1);
        storeDAO.addStore(store);

        // Видалення запису
        assertTrue(storeDAO.deleteStore(store.getId()));

        // Перевірка, що запис видалений
        assertNull(storeDAO.getStoreById(store.getId()));
    }
}
