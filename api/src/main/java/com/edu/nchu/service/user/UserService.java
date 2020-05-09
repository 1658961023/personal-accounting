package com.edu.nchu.service.user;

import com.edu.nchu.entity.User;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： UserService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/27 下午 09:45
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface UserService {

    User getUserByAcct(String acct);

    void updateSelective(User user);
}

