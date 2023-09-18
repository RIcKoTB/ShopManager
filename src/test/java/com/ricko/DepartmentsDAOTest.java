package com.ricko;

import com.example.accountingofgoods.da.entity.Departments;
import com.example.accountingofgoods.dao.tables.DepartmentsDAO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentsDAOTest {
    private Connection connection;
    private DepartmentsDAO departmentsDAO;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";

    @Before
    public void setUp() throws SQLException {
        // Установлення з'єднання з базою даних перед кожним тестом
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        departmentsDAO = new DepartmentsDAO(connection);
    }

    @After
    public void tearDown() throws SQLException {
        // Закриття з'єднання з базою даних після кожного тесту
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddDepartments() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }
        Departments departments = new Departments(10, "City", "Street", image);
        assertTrue(departmentsDAO.addDepartments(departments));
    }

    @Test
    public void testGetDepartmentsById() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }
        Departments departments = new Departments(1, "City", "Street", image);
        departmentsDAO.addDepartments(departments);

        Departments retrievedDepartments = departmentsDAO.getDepartmentsById(1);
        assertNotNull(retrievedDepartments);
        assertEquals(1, retrievedDepartments.getId());
        assertEquals("City", retrievedDepartments.getCity());
        assertEquals("Street", retrievedDepartments.getStreet());
    }

    @Test
    public void testGetAllDepartments() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }

        Departments departments1 = new Departments(1, "City1", "Street1", image);
        Departments departments2 = new Departments(2, "City2", "Street2", image);
        departmentsDAO.addDepartments(departments1);
        departmentsDAO.addDepartments(departments2);

        List<Departments> departmentsList = departmentsDAO.getAllDepartments();
        assertNotNull(departmentsList);
        assertEquals(2, departmentsList.size());

    }

    @Test
    public void testUpdateDepartments() {
        int width = 100;
        int height = 100;

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setArgb(x, y, 0xFF000000);
            }
        }

        Departments departments = new Departments(1, "City", "Street", image);

        departments.setCity("New City");
        departments.setStreet("New Street");

        assertTrue(departmentsDAO.updateDepartments(departments));

        Departments updatedDepartments = departmentsDAO.getDepartmentsById(2);
        assertNotNull(updatedDepartments);
        assertEquals("New City", updatedDepartments.getCity());
        assertEquals("New Street", updatedDepartments.getStreet());
        assertEquals(departments.getPhoto(), updatedDepartments.getPhoto());
    }

    @Test
    public void testDeleteDepartments() {
       int id = 20;
       departmentsDAO.deleteDepartments(id);
    }
}
