package com.edu.nchu.entity.enums;


/*********************************************************
 @author guoff16201210
  * <p> 文件名称： PageEnum
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.entity.enums
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/3 下午 01:38
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public enum PageEnum implements MyEnum {

    //分页枚举枚举
    PAGE_SIZE("10","系统分类");
    private final String code;
    private final String description;

    PageEnum(String code, String description) {
        this.code = code;
        this.description = description;
    };

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
