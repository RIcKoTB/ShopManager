package com.example.accountingofgoods.db.functional;

import com.example.accountingofgoods.db.Connect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас StructureTable містить метод для отримання структури таблиці.
 */
public class StructureTable {
    /**
     * Отримує список назв стовпців таблиці.
     *
     * @param tableName Назва таблиці.
     * @return Список назв стовпців.
     */
    public static List<String> getColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        try (Connection conn = Connect.connect()) {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                columns.add(columnName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return columns;
    }
}
