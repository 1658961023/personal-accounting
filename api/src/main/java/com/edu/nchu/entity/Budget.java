package com.edu.nchu.entity;


import java.io.Serializable;

public class Budget implements Serializable {
    private Integer id;

    private String budgetAmount;

    private int dateType;

    private String startDate;

    private String endDate;

    private String totalAmount;

    private String category;

    private String dAmount;

    private int expireFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount == null ? null : budgetAmount.trim();
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(Short dateType) {
        this.dateType = dateType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount == null ? null : totalAmount.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getdAmount() {
        return dAmount;
    }

    public void setdAmount(String dAmount) {
        this.dAmount = dAmount == null ? null : dAmount.trim();
    }

    public int getExpireFlag() {
        return expireFlag;
    }

    public void setExpireFlag(Short expireFlag) {
        this.expireFlag = expireFlag;
    }
}