package com.example.accountingofgoods.db;

import java.util.List;

/**
 * Інтерфейс CRUDOperation визначає базові операції створення, читання, оновлення та видалення даних.
 *
 * @param <T> Тип даних для операцій CRUD.
 */
public interface CRUDOperation<T> {
    /**
     * Створює новий рядок даних у таблиці.
     *
     * @param instanceTableRow Об'єкт, що представляє рядок даних для створення.
     */
    void createNewRow(T instanceTableRow);

    /**
     * Оновлює рядок даних у таблиці за заданим ідентифікатором.
     *
     * @param id                Ідентифікатор рядка даних, який потрібно оновити.
     * @param instanceTableRow Об'єкт, що представляє рядок даних для оновлення.
     */
    void updateRow(int id, T instanceTableRow);

    /**
     * Повертає список всіх рядків даних у таблиці.
     *
     * @param nameTable Назва таблиці.
     * @return Список списків, де кожен список представляє рядок даних.
     */
    List<List> readAll(String nameTable);

    /**
     * Повертає рядок даних за заданим ідентифікатором.
     *
     * @param id Ідентифікатор рядка даних, який потрібно прочитати.
     * @return Список, що представляє рядок даних.
     */
    List readById(int id);

    /**
     * Видаляє рядок даних за заданим ідентифікатором.
     *
     * @param id Ідентифікатор рядка даних, який потрібно видалити.
     */
    void deleteByID(int id);

    /**
     * Повертає назву таблиці.
     *
     * @return Назва таблиці.
     */
    String getNameTable();
}
