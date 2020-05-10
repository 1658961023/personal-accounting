package com.edu.nchu.component;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： CustomExceptionHandler
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.component 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/9 下午 09:53
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String uploadException(MaxUploadSizeExceededException e,RedirectAttributes redirectAttributes) throws IOException {
        redirectAttributes.addAttribute("msg","文件大小超出3MB限制,请压缩或降低文件质量");
        return "redirect:userInfo";
        }
    }
