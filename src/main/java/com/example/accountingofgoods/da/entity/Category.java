package com.example.accountingofgoods.da.entity;

/**
 * Клас, що представляє категорію товару.
 */
public class Category {
    private int id;
    private String name;

    /**
     * Конструктор класу Category.
     *
     * @param name Назва категорії.
     */
    public Category(String name) {
        this.name = name;
    }

    public Category(int id) {
        this.id = id;
    }

    /**
     * Отримує ідентифікатор категорії.
     *
     * @return Ідентифікатор категорії.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор категорії.
     *
     * @param id Ідентифікатор категорії.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує назву категорії.
     *
     * @return Назва категорії.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву категорії.
     *
     * @param name Назва категорії.
     */
    public void setName(String name) {
        this.name = name;
    }
}
