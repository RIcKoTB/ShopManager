package com.example.accountingofgoods.da.entity;

/**
 * Клас, що представляє інформацію про товар у магазині.
 */
public class Store {
    private int id;
    private String name;
    private int goodsId;
    private int count;
    private int departmentId;
    private int distributorId;

    /**
     * Конструктор класу Store.
     *
     * @param id            Ідентифікатор товару в магазині.
     * @param name          Назва товару.
     * @param goodsId       Ідентифікатор товару.
     * @param count         Кількість товару в магазині.
     * @param departmentId  Ідентифікатор відділу магазину.
     * @param distributorId Ідентифікатор дистриб'ютора товару.
     */
    public Store(int id, String name, int goodsId, int count, int departmentId, int distributorId) {
        this.id = id;
        this.name = name;
        this.goodsId = goodsId;
        this.count = count;
        this.departmentId = departmentId;
        this.distributorId = distributorId;
    }

    /**
     * Отримує ідентифікатор товару в магазині.
     *
     * @return Ідентифікатор товару в магазині.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор товару в магазині.
     *
     * @param id Ідентифікатор товару в магазині.
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
     * Отримує ідентифікатор товару.
     *
     * @return Ідентифікатор товару.
     */
    public int getGoodsId() {
        return goodsId;
    }

    /**
     * Встановлює ідентифікатор товару.
     *
     * @param goodsId Ідентифікатор товару.
     */
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * Отримує кількість товару в магазині.
     *
     * @return Кількість товару в магазині.
     */
    public int getCount() {
        return count;
    }

    /**
     * Встановлює кількість товару в магазині.
     *
     * @param count Кількість товару в магазині.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Отримує ідентифікатор відділу магазину.
     *
     * @return Ідентифікатор відділу магазину.
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Встановлює ідентифікатор відділу магазину.
     *
     * @param departmentId Ідентифікатор відділу магазину.
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Отримує ідентифікатор дистриб'ютора товару.
     *
     * @return Ідентифікатор дистриб'ютора товару.
     */
    public int getDistributorId() {
        return distributorId;
    }

    /**
     * Встановлює ідентифікатор дистриб'ютора товару.
     *
     * @param distributorId Ідентифікатор дистриб'ютора товару.
     */
    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }
}
