package com.edu.nchu.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.User;
import com.edu.nchu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： UserServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/27 下午 09:46
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByAcct(String acct) {
        return userMapper.selectByAcct(acct);
    }

    @Override
    public void updateSelective(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
