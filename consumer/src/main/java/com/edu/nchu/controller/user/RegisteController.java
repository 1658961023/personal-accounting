package com.edu.nchu.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.service.user.RegisteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RegisterController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/24 下午 06:49
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class RegisteController {

    @Reference
    private RegisteService registeService;

    @PostMapping("/register")
    private String register(@RequestParam String acct,
                           @RequestParam String password,
                           @RequestParam String nickname,
                           Map<String,String> map) {
        String url = registeService.register(acct,password,nickname);
        if(("register").equals(url)){
            map.put("msg","账号已被使用，注册失败");
        }else {
            map.put("msg","注册成功！");
        }
        return url;
    }

    @RequestMapping("/register")
    private String register(){
        return "register";
    }
}
