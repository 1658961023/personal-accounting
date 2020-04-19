package com.edu.nchu.entity.enums;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： DateTypeEnum
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.entity.enums 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/17 上午 11:12
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public enum  DateTypeEnum implements MyEnum {

    //周期类型枚举
    MONTH("1","月度");
    private final String code;
    private final String description;

    DateTypeEnum(String code, String description) {
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
