package com.edu.nchu.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.User;
import com.edu.nchu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RegisteServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/24 下午 06:56
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class RegisteServiceImpl implements RegisteService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(String acct, String password, String nickname, Map<String, String> map) {
        if (ObjectUtils.isEmpty(userMapper.selectByAcct(acct))) {
            //账号不重复，可以使用
            User user = new User();
            user.setAcct(acct);
            user.setPassword(password);
            user.setNickname(nickname);
            userMapper.insert(user);
            map.put("msg", "注册成功");
            return "login";
        }
        //账号重复，无法注册
        map.put("msg", "账号已被使用，注册失败");
        return "register";
    }
}
