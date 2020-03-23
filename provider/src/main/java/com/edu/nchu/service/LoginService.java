package com.edu.nchu.service;

import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： LoginService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/20 下午 10:52
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface LoginService {

    String login(String acct, String password, Map<String,String> map);
}

