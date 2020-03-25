package com.edu.nchu.entity;

import java.util.Date;

public class Budget {
    private Integer id;

    private String budgetAmount;

    private Short dateType;

    private Date startDate;

    private Date endDate;

    private String totalAmount;

    private String category;

    private String dAmount;

    private Short expireFlag;

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

    public Short getDateType() {
        return dateType;
    }

    public void setDateType(Short dateType) {
        this.dateType = dateType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public Short getExpireFlag() {
        return expireFlag;
    }

    public void setExpireFlag(Short expireFlag) {
        this.expireFlag = expireFlag;
    }
}