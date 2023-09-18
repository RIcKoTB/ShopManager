package com.example.accountingofgoods.da.entity;

import javafx.scene.image.Image;

/**
 * Клас, що представляє відділення або відділення компанії.
 */
public class Departments {
    int id;
    String city;
    String street;
    Image photo;

    /**
     * Отримує ідентифікатор відділення.
     *
     * @return Ідентифікатор відділення.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор відділення.
     *
     * @param id Ідентифікатор відділення.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує місто, в якому знаходиться відділення.
     *
     * @return Місто відділення.
     */
    public String getCity() {
        return city;
    }

    /**
     * Встановлює місто, в якому знаходиться відділення.
     *
     * @param city Місто відділення.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Отримує вулицю, на якій знаходиться відділення.
     *
     * @return Вулиця відділення.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Встановлює вулицю, на якій знаходиться відділення.
     *
     * @param street Вулиця відділення.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Отримує фото відділення.
     *
     * @return Фото відділення.
     */
    public Image getPhoto() {
        return photo;
    }

    /**
     * Встановлює фото відділення.
     *
     * @param photo Фото відділення.
     */
    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    /**
     * Конструктор класу Departments.
     *
     * @param id     Ідентифікатор відділення.
     * @param city   Місто відділення.
     * @param street Вулиця відділення.
     * @param photo  Фото відділення.
     */
    public Departments(int id, String city, String street, Image photo) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.photo = photo;
    }
}
