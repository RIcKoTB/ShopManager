package com.example.accountingofgoods.da.entity;

import java.time.LocalDateTime;

/**
 * Клас, що представляє історію покупок.
 */
public class HistoryBuy {
    int id;
    int userId;
    int basket_id;
    LocalDateTime date;

    public HistoryBuy(int userId, int basket_id, LocalDateTime date) {
        this.userId = userId;
        this.basket_id = basket_id;
        this.date = date;
    }

    /**
     * Отримує ідентифікатор історії покупки.
     *
     * @return Ідентифікатор історії покупки.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор історії покупки.
     *
     * @param id Ідентифікатор історії покупки.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує ідентифікатор користувача.
     *
     * @return Ідентифікатор користувача.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Встановлює ідентифікатор користувача.
     *
     * @param userId Ідентифікатор користувача.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Отримує ідентифікатор кошика.
     *
     * @return Ідентифікатор кошика.
     */
    public int getBasket_id() {
        return basket_id;
    }

    /**
     * Встановлює ідентифікатор кошика.
     *
     * @param basket_id Ідентифікатор кошика.
     */
    public void setBasket_id(int basket_id) {
        this.basket_id = basket_id;
    }

    /**
     * Отримує дату історії покупки.
     *
     * @return Дата історії покупки.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Встановлює дату історії покупки.
     *
     * @param date Дата історії покупки.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Конструктор класу HistoryBuy.
     *
     * @param id         Ідентифікатор історії покупки.
     * @param userId     Ідентифікатор користувача.
     * @param basket_id  Ідентифікатор кошика.
     * @param date       Дата історії покупки.
     */
    public HistoryBuy(int id, int userId, int basket_id, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.basket_id = basket_id;
        this.date = date;
    }
}
