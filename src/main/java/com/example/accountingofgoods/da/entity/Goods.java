package com.example.accountingofgoods.da.entity;

import javafx.scene.image.Image;

/**
 * Клас, що представляє товар.
 */
public class Goods {
    int id;
    String name;
    Image photo;
    int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    Double price;

    /**
     * Отримує ідентифікатор товару.
     *
     * @return Ідентифікатор товару.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор товару.
     *
     * @param id Ідентифікатор товару.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує назву товару.
     *
     * @return Назва товару.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву товару.
     *
     * @param name Назва товару.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Отримує фото товару.
     *
     * @return Фото товару.
     */
    public Image getPhoto() {
        return photo;
    }

    /**
     * Встановлює фото товару.
     *
     * @param photo Фото товару.
     */
    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    /**
     * Отримує ціну товару.
     *
     * @return Ціна товару.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну товару.
     *
     * @param price Ціна товару.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    public Goods(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Goods(int id) {
        this.id = id;
    }

    public Goods(String name, Image photo, int categoryId, Double price) {
        this.name = name;
        this.photo = photo;
        this.categoryId = categoryId;
        this.price = price;
    }

    public Goods(int id, String name, Image photo, int categoryId, Double price) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.categoryId = categoryId;
        this.price = price;
    }
}
