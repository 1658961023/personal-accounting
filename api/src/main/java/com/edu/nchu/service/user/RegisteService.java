package com.edu.nchu.service.user;

import com.alibaba.dubbo.config.annotation.Service;

import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RegisteService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/24 下午 06:53
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface RegisteService {

    String register(String acct, String password, String nickname);
}

