package com.example.accountingofgoods.db.functional;

import java.util.ArrayList;
import java.util.List;

import static com.example.accountingofgoods.db.functional.StructureTable.getColumns;

/**
 * Клас Statements містить методи для генерації SQL-запитів.
 */
public class Statements {
    /**
     * Генерує SQL-запит для вставки нового рядка в таблицю.
     *
     * @param nameTable Назва таблиці.
     * @return SQL-запит для вставки рядка.
     */
    public static String Insert(String nameTable) {
        List<String> sqlInsertStructure = DynamicAdd(nameTable);
        String sql = "INSERT INTO " + nameTable + " (" + sqlInsertStructure.get(0) + ") VALUES (" + sqlInsertStructure.get(1) + ");";
        return sql;
    }

    /**
     * Генерує список частин SQL-запиту для динамічного додавання рядка в таблицю.
     *
     * @param nameTable Назва таблиці.
     * @return Список частин SQL-запиту.
     */
    public static List<String> DynamicAdd(String nameTable){
        List<String> partsStatementSQL = new ArrayList<>();
        StringBuilder sqlInfo = new StringBuilder();
        StringBuilder sqlLocation = new StringBuilder();
        boolean switcher = false;

        for (String el : getColumns(nameTable)) {
            if (!switcher) {
                switcher = true;
                sqlLocation.append(el);
                sqlInfo.append("?");
            } else {
                sqlLocation.append(",").append(el);
                sqlInfo.append(",?");
            }
        }
        partsStatementSQL.add(sqlLocation.toString());
        partsStatementSQL.add(sqlInfo.toString());
        return partsStatementSQL;
    }

    /**
     * Генерує SQL-запит для оновлення рядка в таблиці.
     *
     * @param nameTable Назва таблиці.
     * @return SQL-запит для оновлення рядка.
     */
    public static String Update(String nameTable) {
        List<String> sqlInsertStructure = DynamicAdd(nameTable);
        StringBuilder dynamicSqlPart = new StringBuilder();
        String[] elements = sqlInsertStructure.get(0).split(",");
        for (int i = 1; i < elements.length; i++) {
            if (i > 1) {
                dynamicSqlPart.append(", ");
            }
            dynamicSqlPart.append(elements[i]).append(" = ?");
        }
        String sql = "UPDATE " + nameTable + " SET " + dynamicSqlPart + " WHERE id = ?";
        return sql;
    }

    /**
     * Генерує SQL-запит для видалення рядка з таблиці за заданим ідентифікатором.
     *
     * @param nameTable Назва таблиці.
     * @return SQL-запит для видалення рядка.
     */
    public static String Delete(String nameTable) {
        String sql = "DELETE FROM "+nameTable+" WHERE id = ?";
        return sql;
    }

    /**
     * Генерує SQL-запит для читання рядка з таблиці за заданим ідентифікатором.
     *
     * @param nameTable Назва таблиці.
     * @param id        Ідентифікатор рядка.
     * @return SQL-запит для читання рядка.
     */
    public static String Read(String nameTable, int id) {
        String sql = "SELECT "+ DynamicAdd(nameTable).get(0)+" FROM " + nameTable + " WHERE id = "+id+"; ";
        return sql;
    }

    /**
     * Генерує SQL-запит для читання всіх рядків з таблиці.
     *
     * @param nameTable Назва таблиці.
     * @return SQL-запит для читання всіх рядків.
     */
    public static String ReadAll(String nameTable) {
        String sql = "SELECT * FROM " + nameTable;
        return sql;
    }
}
