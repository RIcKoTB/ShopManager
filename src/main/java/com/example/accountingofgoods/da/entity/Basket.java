package com.example.accountingofgoods.da.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Клас, що представляє кошик з товарами.
 */
public class Basket {
    int id;
    int userId;
    List<Goods> goodsList;
    LocalDateTime date;

    /**
     * Отримує ідентифікатор кошика.
     *
     * @return Ідентифікатор кошика.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор кошика.
     *
     * @param id Ідентифікатор кошика.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує ідентифікатор користувача, до якого належить кошик.
     *
     * @return Ідентифікатор користувача.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Встановлює ідентифікатор користувача, до якого належить кошик.
     *
     * @param userId Ідентифікатор користувача.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Отримує список товарів у кошику.
     *
     * @return Список товарів у кошику.
     */
    public List<Goods> getGoodsList() {
        return goodsList;
    }

    /**
     * Встановлює список товарів у кошику.
     *
     * @param goodsList Список товарів у кошику.
     */
    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    /**
     * Отримує дату та час створення кошика.
     *
     * @return Дата та час створення кошика.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Встановлює дату та час створення кошика.
     *
     * @param date Дата та час створення кошика.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Basket(int userId, List<Goods> goodsList, LocalDateTime date) {
        this.userId = userId;
        this.goodsList = goodsList;
        this.date = date;
    }

    /**
     * Конструктор класу Basket.
     *
     * @param id         Ідентифікатор кошика.
     * @param userId     Ідентифікатор користувача.
     * @param goodsList  Список товарів у кошику.
     * @param date       Дата та час створення кошика.
     */
    public Basket(int id, int userId, List<Goods> goodsList, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.goodsList = goodsList;
        this.date = date;
    }
}
