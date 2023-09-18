package com.example.accountingofgoods.dao;

import com.example.accountingofgoods.db.CRUDOperation;
import com.example.accountingofgoods.db.Connect;
import com.example.accountingofgoods.db.functional.Statements;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас MainDao реалізує інтерфейс CRUDOperation і надає базову реалізацію операцій CRUD (створення, читання, оновлення, видалення) для сутності.
 * Він також забезпечує з'єднання з базою даних та виконання SQL-запитів.
 *
 * @param <T> Тип сутності, з якою працює клас MainDao.
 */
public class MainDao<T> implements CRUDOperation<T> {

    /**
     * Створює новий рядок у таблиці бази даних з вказаною сутністю.
     *
     * @param instanceTableRow Сутність, яка представляє рядок таблиці бази даних.
     */
    @Override
    public void createNewRow(T instanceTableRow) {
        String sql = Statements.Insert(getNameTable());
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int i = 1;
            for (Field field : instanceTableRow.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(instanceTableRow);
                if (i == 1 && !value.equals(0)) {
                    pstmt.setObject(i, value);
                } else if (i > 1) {
                    pstmt.setObject(i, value);
                }
                i++;
            }
            pstmt.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Оновлює рядок у таблиці бази даних з вказаним ідентифікатором за допомогою нової сутності.
     *
     * @param id                Ідентифікатор рядка, який потрібно оновити.
     * @param instanceTableRow Нова сутність, яка представляє оновлений рядок таблиці бази даних.
     */
    @Override
    public void updateRow(int id, T instanceTableRow) {
        String sql = Statements.Update(getNameTable());
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int i = 0;
            for (Field field : instanceTableRow.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(instanceTableRow);
                    if (i != 0 || !value.equals(0)) {
                        pstmt.setObject(i, value);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                i++;
            }
            pstmt.setInt(i, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Повертає список всіх рядків з вказаної таблиці бази даних.
     *
     * @param nameTable Назва таблиці бази даних.
     * @return Список списків, кожен з яких представляє рядок таблиці бази даних.
     */
    @Override
    public List<List> readAll(String nameTable) {
        List<List> result = new ArrayList<>();
        String[] nameColumns = Statements.DynamicAdd(nameTable).get(0).split(",");
        String sql = Statements.ReadAll(nameTable);
        try (Connection conn = Connect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (String columnName : nameColumns) {
                    Object value = rs.getObject(columnName);
                    try {
                        row.add(value.toString());
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                result.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Повертає список значень рядка з вказаним ідентифікатором.
     *
     * @param id Ідентифікатор рядка, який потрібно прочитати.
     * @return Список значень рядка таблиці бази даних.
     */
    @Override
    public List<Object> readById(int id) {
        List<Object> result = new ArrayList<>();
        String[] nameColumns = Statements.DynamicAdd(getNameTable()).get(0).split(",");
        String sql = Statements.Read(getNameTable(), id);
        try (Connection conn = Connect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                for (String columnName : nameColumns) {
                    Object value = rs.getObject(columnName);
                    try {
                        result.add(value.toString());
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Видаляє рядок з вказаним ідентифікатором з таблиці бази даних.
     *
     * @param id Ідентифікатор рядка, який потрібно видалити.
     */
    @Override
    public void deleteByID(int id) {
        String sql = Statements.Delete(getNameTable());
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Повертає назву таблиці.
     *
     * @return Назва таблиці.
     */
    public String getNameTable() {
        return null;
    }
}
