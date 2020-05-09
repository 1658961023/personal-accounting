package com.edu.nchu.entity;


import java.io.Serializable;

public class Target implements Serializable {
    private Integer id;

    private String targetAmount;

    private String dateType;

    private String startDate;

    private String endDate;

    private String totalAmount;

    private String category;

    private String dAmount;

    private String expireFlag;

    private String acct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
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
        this.totalAmount = totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getdAmount() {
        return dAmount;
    }

    public void setdAmount(String dAmount) {
        this.dAmount = dAmount;
    }

    public String getExpireFlag() {
        return expireFlag;
    }

    public void setExpireFlag(String expireFlag) {
        this.expireFlag = expireFlag;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }
}