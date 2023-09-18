package com.ricko;

import com.example.accountingofgoods.da.entity.Distributors;
import com.example.accountingofgoods.dao.tables.DistributorsDAO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DistributorsDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";
    private static Connection connection;
    private DistributorsDAO distributorsDAO;

    @BeforeAll
    public static void setupConnection() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    @BeforeEach
    public void setUp() {
        distributorsDAO = new DistributorsDAO(connection);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void testAddDistributor() {
        Distributors distributor = createDummyDistributor();

        assertTrue(distributorsDAO.addDistributor(distributor), "Failed to add distributor");
    }

    @Test
    public void testUpdateDistributor() {
        Distributors distributor = createDummyDistributor();

        distributor.setFull_name("Updated Name");
        distributor.setGoodsID(1);
        distributor.setCompany("Updated Company");
        Image updatedLogo = createDummyImage(100, 100);
        distributor.setLogo(updatedLogo);

        assertTrue(distributorsDAO.updateDistributor(distributor), "Failed to update distributor");

        Distributors updatedDistributor = distributorsDAO.getDistributorById(distributor.getId());
        assertNotNull(updatedDistributor, "Failed to retrieve updated distributor");
        assertEquals(distributor.getFull_name(), updatedDistributor.getFull_name(), "Incorrect updated full name");
        assertEquals(distributor.getGoodsID(), updatedDistributor.getGoodsID(), "Incorrect updated goods ID");
        assertEquals(distributor.getCompany(), updatedDistributor.getCompany(), "Incorrect updated company");
        assertImagesEqual(distributor.getLogo(), updatedDistributor.getLogo());
    }

    @Test
    public void testDeleteDistributor() {
       int id = 1;
       distributorsDAO.deleteDistributor(id);
    }

    // Helper methods

    private Distributors createDummyDistributor() {
        Image dummyLogo = createDummyImage(100, 100);
        return new Distributors(1, "John Doe", 1, "ABC Company", dummyLogo);
    }

    private Image createDummyImage(int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private void assertImagesEqual(Image expectedImage, Image actualImage) {
        // Compare images based on their properties (e.g., dimensions, color, etc.)
        // Implement the comparison logic according to your specific requirements
        // For simplicity, this method assumes the images are equal if their dimensions are the same

        assertEquals(expectedImage.getWidth(), actualImage.getWidth(), "Incorrect image width");
        assertEquals(expectedImage.getHeight(), actualImage.getHeight(), "Incorrect image height");
    }
}
