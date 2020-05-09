package com.edu.nchu.DTO;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： CategoryDto
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.DTO 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/5 上午 11:31
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public class CategoryDto {

    private String name;

    private String budgetType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }
}
