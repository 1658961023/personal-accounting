package com.edu.nchu.entity.enums;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： PayEnum
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.entity.enums 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/10 上午 09:52
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public enum PayEnum{

    //系统默认五个虚拟账户，注册用户时添加给每个用户
    ALI_PAY("1","支付宝"),
    WECHAT_PAY("2","微信"),
    CASH("3","现金"),
    DEBIT_CARD("4","储蓄卡"),
    CREDIT_CARD("5","信用卡");

    private final String name;
    private final String desc;

    PayEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByName(String name){
        for (PayEnum payEnum : PayEnum.values()) {
            if(name.equals(payEnum.getName())){
                return payEnum.getDesc();
            }
        }
        return null;
    }
}
