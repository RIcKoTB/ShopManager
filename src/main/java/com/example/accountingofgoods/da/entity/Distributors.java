package com.example.accountingofgoods.da.entity;

import javafx.scene.image.Image;

/**
 * Клас, що представляє дистриб'ютора або компанію-дистриб'ютора.
 */
public class Distributors {
    int id;
    String full_name;
    int goodsID;
    String company;
    Image logo;

    public int getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(int goodsID) {
        this.goodsID = goodsID;
    }

    /**
     * Отримує ідентифікатор дистриб'ютора.
     *
     * @return Ідентифікатор дистриб'ютора.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор дистриб'ютора.
     *
     * @param id Ідентифікатор дистриб'ютора.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує повне ім'я дистриб'ютора.
     *
     * @return Повне ім'я дистриб'ютора.
     */
    public String getFull_name() {
        return full_name;
    }

    /**
     * Встановлює повне ім'я дистриб'ютора.
     *
     * @param full_name Повне ім'я дистриб'ютора.
     */
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    /**
     * Отримує назву компанії дистриб'ютора.
     *
     * @return Назва компанії дистриб'ютора.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Встановлює назву компанії дистриб'ютора.
     *
     * @param company Назва компанії дистриб'ютора.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Отримує логотип дистриб'ютора.
     *
     * @return Логотип дистриб'ютора.
     */
    public Image getLogo() {
        return logo;
    }

    /**
     * Встановлює логотип дистриб'ютора.
     *
     * @param logo Логотип дистриб'ютора.
     */
    public void setLogo(Image logo) {
        this.logo = logo;
    }

    /**
     * Конструктор класу Distributors.
     *
     * @param id        Ідентифікатор дистриб'ютора.
     * @param full_name Повне ім'я дистриб'ютора.
     * @param company   Назва компанії дистриб'ютора.
     * @param logo      Логотип дистриб'ютора.
     */
    public Distributors(int id, String full_name, int goodsID, String company, Image logo) {
        this.id = id;
        this.full_name = full_name;
        this.goodsID = goodsID;
        this.company = company;
        this.logo = logo;
    }
}
