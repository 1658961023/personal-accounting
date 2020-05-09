package com.edu.nchu.entity.enums;

import com.edu.nchu.entity.Category;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： CategoryEnum
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.entity.enums 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/5 上午 11:23
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public enum CategoryEnum{

    //系统默认分类，注册用户时添加给每个用户
    SALARY("工资","0"),
    PART_TIME("兼职","0"),
    MONEY_MANAGE("理财","0"),
    OTHER("其他","0"),
    EATING("餐饮","1"),
    DAILY("日用","1"),
    TRANSPORT("交通","1"),
    ENTERTAINMENT("娱乐","1"),
    CLOTHING("服饰","1"),
    TRAVEL("旅游","1"),
    STUDY("学习","1"),
    INVESTMENT("投资","1");

    private final String name;
    private final String budgetType;

    CategoryEnum(String name, String budgetType) {
        this.name = name;
        this.budgetType = budgetType;
    }

    public String getName() {
        return name;
    }

    public String getBudgetType() {
        return budgetType;
    }
}
