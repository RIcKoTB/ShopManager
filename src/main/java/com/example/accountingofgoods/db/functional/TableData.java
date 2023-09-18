package com.example.accountingofgoods.db.functional;

import com.example.accountingofgoods.db.Connect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас TableData містить методи для отримання даних про таблиці.
 */
public class TableData {
    /**
     * Отримує список назв стовпців таблиці.
     *
     * @param tableName Назва таблиці.
     * @return Список назв стовпців.
     */
    public static List<String> getTableColumns(String tableName) {
        List<String> columns = new ArrayList<>();

        try (Connection conn = Connect.connect()) {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");
                if (columnType.equalsIgnoreCase("BLOB")) {
                    columns.add(columnName);
                } else {
                    columns.add(columnName);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return columns;
    }

    /**
     * Перевіряє, чи є стовпець типу BLOB в таблиці.
     *
     * @param tableName  Назва таблиці.
     * @param columnName Назва стовпця.
     * @return true, якщо стовпець є типу BLOB, в іншому випадку false.
     */
    public static boolean isBlobColumn(String tableName, String columnName) {
        try (Connection conn = Connect.connect()) {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                String currentColumnName = rs.getString("COLUMN_NAME");
                if (currentColumnName.equals(columnName)) {
                    String columnType = rs.getString("TYPE_NAME");
                    return columnType.equalsIgnoreCase("BLOB");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
