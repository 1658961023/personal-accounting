package com.edu.nchu.controller.budgetTarget;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.User;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.entity.enums.DateTypeEnum;
import com.edu.nchu.service.budgetTarget.BudgetTargetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： TargetController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.budgetTarget
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/16 上午 11:30
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class TargetController {

    @Reference
    private BudgetTargetService budgetTargetService;

    @RequestMapping("/budgetTarget")
    private String budgetTarget(Map<String,Object> map,
                                HttpSession session){
        User user = (User) session.getAttribute("user");
        map.put("budget",budgetTargetService.getBudget(DateTypeEnum.MONTH.getCode(),user.getAcct()));
        map.put("target",budgetTargetService.getTarget(DateTypeEnum.MONTH.getCode(),user.getAcct()));
        return "budgetTarget/budgetTarget";
    }

    @GetMapping("/getBudgetForChart")
    @ResponseBody
    private Object getBudgetTarget(@RequestParam String month,
                                   @RequestParam String budgetTarget,
                                   HttpSession session){
        User user = (User) session.getAttribute("user");
        if(BudgetEnum.BUDGET.getCode().equals(budgetTarget)){
            return budgetTargetService.getBudget(DateTypeEnum.MONTH.getCode(),user.getAcct());
        }
        return budgetTargetService.getTarget(DateTypeEnum.MONTH.getCode(),user.getAcct());
    }

    @RequestMapping("editBudget")
    private String editBudget(@RequestParam String budgetAmount,
                              @RequestParam String dateType,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        budgetTargetService.editBudget(budgetAmount,dateType,user.getAcct());
        return "redirect:budgetTarget";
    }

    @RequestMapping("editTarget")
    private String editTarget(@RequestParam String targetAmount,
                              @RequestParam String dateType,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        budgetTargetService.editTarget(targetAmount,dateType,user.getAcct());
        return "redirect:budgetTarget";
    }
}
