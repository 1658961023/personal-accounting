package com.edu.nchu.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.User;
import com.edu.nchu.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： UserController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.user 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/29 下午 02:47
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/userInfo")
    private String userInfo(HttpSession session,
                            Map<String, Object> map,
                            HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        map.put("user", userService.getUserByAcct(user.getAcct()));
        if (!StringUtils.isEmpty(request.getParameter("msg"))) {
            map.put("msg", request.getParameter("msg"));
        }
        return "user/userInfo";
    }

    @RequestMapping("/index")
    private String index() {
        return "index";
    }

    @RequestMapping("/changePwd")
    private String changePassword(HttpSession session,
                                  Map<String, Object> map) {
        User user = (User) session.getAttribute("user");
        map.put("user", userService.getUserByAcct(user.getAcct()));
        return "user/changePwd";
    }

    @RequestMapping("/changePwdDo")
    private String changePwdDo(HttpSession session,
                               @RequestParam String newPassword,
                               Map<String, Object> map) {
        User user = (User) session.getAttribute("user");
        user.setPassword(newPassword);
        userService.updateSelective(user);
        map.put("msg", "修改成功，请使用新密码重新登录");
        return "redirect:loginOut";
    }

    @RequestMapping("/updateUserInfo")
    private String updateUserInfo(@RequestParam("profile") MultipartFile profile,
                                  @RequestParam("acct") String acct,
                                  @RequestParam("nickname") String nickname,
                                  @RequestParam("sex") String sex,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("email") String email,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {
        User user = userService.getUserByAcct(acct);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setPhone(phone);
        user.setEmail(email);
        user.setProfile(StringUtils.isEmpty(user.getProfile()) ? "" : user.getProfile());
        String fileName = System.currentTimeMillis() + profile.getOriginalFilename();
        String filePath = "C:\\Users\\msmw\\IdeaProjects\\personal-accounting\\consumer\\src\\main\\resources\\static\\images\\profiles\\";
        String suffix = profile.getContentType().toLowerCase();
        suffix = suffix.substring(suffix.lastIndexOf("/") + 1);
        if (!StringUtils.isEmpty(profile.getOriginalFilename())) {
            if ("jpeg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix)) {
                File dest = new File(filePath + fileName);
                try {
                    profile.transferTo(dest);
                    user.setProfile(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:userInfo";
                }
            } else {
                redirectAttributes.addAttribute("msg", "文件格式不正确,仅支持JPG,PNG,GIF");
                return "redirect:userInfo";
            }

        }
        userService.updateSelective(user);
        session.setAttribute("user", user);
        return "redirect:userInfo";
    }
}
