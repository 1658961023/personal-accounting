package com.edu.nchu.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.service.user.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： LoginController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/20 下午 10:55
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class LoginController {

    @Reference(version = "1.0")
    private LoginService loginService;

    @PostMapping("/login")
    private String login(@RequestParam String acct,
                         @RequestParam String password,
                         Map<String, String> map,
                         HttpSession session) {
        if (loginService.check(acct, password)) {
            session.setAttribute("user", acct);
            return "index";
        }
        map.put("msg", "账号或密码错误");
        return "login";
    }

    @RequestMapping({"/", "/login"})
    private String login() {
        return "login";
    }
}
