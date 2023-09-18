package com.example.accountingofgoods.da.entity;

import java.time.LocalDateTime;

/**
 * Клас, що представляє історію розподілу.
 */
public class HistoryDistributor {
    int id;
    int distributorId;
    int departmentId;
    LocalDateTime date;

    /**
     * Отримує ідентифікатор історії розподілу.
     *
     * @return Ідентифікатор історії розподілу.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор історії розподілу.
     *
     * @param id Ідентифікатор історії розподілу.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує ідентифікатор дистриб'ютора.
     *
     * @return Ідентифікатор дистриб'ютора.
     */
    public int getDistributorId() {
        return distributorId;
    }

    /**
     * Встановлює ідентифікатор дистриб'ютора.
     *
     * @param distributorId Ідентифікатор дистриб'ютора.
     */
    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    /**
     * Отримує ідентифікатор відділу.
     *
     * @return Ідентифікатор відділу.
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Встановлює ідентифікатор відділу.
     *
     * @param departmentId Ідентифікатор відділу.
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Отримує дату історії розподілу.
     *
     * @return Дата історії розподілу.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Встановлює дату історії розподілу.
     *
     * @param date Дата історії розподілу.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Конструктор класу HistoryDistributor.
     *
     * @param id            Ідентифікатор історії розподілу.
     * @param distributorId Ідентифікатор дистриб'ютора.
     * @param departmentId  Ідентифікатор відділу.
     * @param date          Дата історії розподілу.
     */
    public HistoryDistributor(int id, int distributorId, int departmentId, LocalDateTime date) {
        this.id = id;
        this.distributorId = distributorId;
        this.departmentId = departmentId;
        this.date = date;
    }
}
