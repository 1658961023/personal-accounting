package com.edu.nchu.entity.enums;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： BillTypeEnum
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.entity.enums 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/8 下午 04:43
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public enum BillTypeEnum implements MyEnum {

    //查看的账单类型，查看本年的月度账单和所有有记录的月份账单
    THIS_YEAR("0","本年"),
    ALL("1","全部");
    private final String code;
    private final String description;

    BillTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
