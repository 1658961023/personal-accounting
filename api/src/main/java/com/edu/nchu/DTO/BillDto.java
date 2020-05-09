package com.edu.nchu.DTO;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： BillDto
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.DTO 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/7 下午 05:19
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public class BillDto {

    private String month;

    private String income;

    private String expend;

    private String balance;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpend() {
        return expend;
    }

    public void setExpend(String expend) {
        this.expend = expend;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
