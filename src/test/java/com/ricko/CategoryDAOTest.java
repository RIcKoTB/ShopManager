package com.ricko;

import static org.junit.jupiter.api.Assertions.*;

import com.example.accountingofgoods.da.entity.Category;
import com.example.accountingofgoods.dao.tables.CategoryDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAOTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/accounting_of_goods1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adidas200415";

    private Connection connection;
    private CategoryDAO categoryDAO;

    @BeforeEach
    public void setup() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        categoryDAO = new CategoryDAO();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testAddCategory() throws SQLException {
        Category category = new Category("Test Category");
        categoryDAO.addCategory(category);

        int categoryId = category.getId();
        Category retrievedCategory = categoryDAO.getCategoryById(categoryId);
        assertNotNull(retrievedCategory);
        assertEquals(categoryId, retrievedCategory.getId());
        assertEquals(category.getName(), retrievedCategory.getName());
        System.out.println(retrievedCategory.getId() + " " + retrievedCategory.getName());
    }

    @Test
    public void testUpdateCategory() throws SQLException {
        Category category = new Category("Test Category");
        categoryDAO.addCategory(category);
        System.out.println("Старе: " + category.getId() + " " + category.getName());

        int categoryId = category.getId();
        category.setName("Updated Category");
        categoryDAO.updateCategory(category);

        Category retrievedCategory = categoryDAO.getCategoryById(categoryId);
        assertNotNull(retrievedCategory);
        assertEquals(categoryId, retrievedCategory.getId());
        assertEquals(category.getName(), retrievedCategory.getName());

        System.out.println("Нове: " + retrievedCategory.getId() + " " + retrievedCategory.getName());
    }

    @Test
    public void testDeleteCategory() throws SQLException {
        Category category = new Category("Test Category");
        categoryDAO.addCategory(category);

        int categoryId = category.getId();
        categoryDAO.deleteCategory(categoryId);

        Category retrievedCategory = categoryDAO.getCategoryById(categoryId);
        assertNull(retrievedCategory);

        System.out.println("Видалено");
    }

    @Test
    public void testGetCategoryById() throws SQLException {
        Category category = new Category("Test Category");
        categoryDAO.addCategory(category);

        int categoryId = category.getId();
        Category retrievedCategory = categoryDAO.getCategoryById(categoryId);
        assertNotNull(retrievedCategory);
        assertEquals(categoryId, retrievedCategory.getId());
        assertEquals(category.getName(), retrievedCategory.getName());

        System.out.println(retrievedCategory.getId() + " " + retrievedCategory.getName());
    }

    @Test
    public void testGetAllCategories() throws SQLException {
        Category category1 = new Category("Category 1");
        categoryDAO.addCategory(category1);

        Category category2 = new Category("Category 2");
        categoryDAO.addCategory(category2);

        List<Category> categories = categoryDAO.getAllCategories();
        assertNotNull(categories);

        for(int i = 0; i < categories.size(); i++){
            System.out.println(categories.get(i).getId() + " " + categories.get(i).getName());
        }

    }
}
