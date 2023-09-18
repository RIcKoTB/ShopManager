package com.ricko;

import com.example.accountingofgoods.da.entity.Goods;
import com.example.accountingofgoods.dao.tables.GoodsDAO;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class GoodsDAOTest {
    private Connection connection;
    private GoodsDAO goodsDAO;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";
    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        goodsDAO = new GoodsDAO();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        // Close the database connection
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndGetGoods() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }

        Goods goods = new Goods(10, "Sample Goods", image, 1, 9.99);

        assertTrue(goodsDAO.addGoods(goods));

        Goods retrievedGoods = goodsDAO.getGoodsById(goods.getId());
        assertNotNull(retrievedGoods);
        assertEquals(goods.getId(), retrievedGoods.getId());
        assertEquals(goods.getName(), retrievedGoods.getName());
        assertEquals(goods.getCategoryId(), retrievedGoods.getCategoryId());
        assertEquals(goods.getPrice(), retrievedGoods.getPrice());

        Image originalImage = goods.getPhoto();
        Image retrievedImage = retrievedGoods.getPhoto();
        assertNotNull(originalImage);
        assertNotNull(retrievedImage);
        assertEquals(originalImage.getWidth(), retrievedImage.getWidth());
        assertEquals(originalImage.getHeight(), retrievedImage.getHeight());

    }

    @Test
    public void testGetAllGoods() {
        List<Goods> goodsList = goodsDAO.getAllGoods();
        assertNotNull(goodsList);
        assertFalse(goodsList.isEmpty());

    }

    @Test
    public void testUpdateGoods() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }

        Goods goods = new Goods(6, "Vasya", image, 2, 20.0);

        boolean updateGoods = goodsDAO.updateGoods(goods);
        assertTrue(updateGoods);
    }

    @Test
    public void testDeleteGoods() {
        int goodsID = 3;
        boolean deleteGoods = goodsDAO.deleteGoods(goodsID);

    }
}
