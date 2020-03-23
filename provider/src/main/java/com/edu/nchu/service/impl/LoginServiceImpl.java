package com.edu.nchu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.service.LoginService;

import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： LoginServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.impl
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/20 下午 09:17
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service(version = "1.0")
public class LoginServiceImpl implements LoginService {

    @Override
    public String login(String acct, String password, Map<String,String> map) {
        return "login";
    }
}
