package com.edu.nchu.controller.acctounting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.User;
import com.edu.nchu.entity.enums.PayEnum;
import com.edu.nchu.service.accounting.RecordService;
import com.edu.nchu.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/*********************************************************
 @author guoff16201210
  * <p> 文件名称： acctountingController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.acctounting 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/27 下午 06:02
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class AccountingController {

    @Reference
    private RecordService recordService;

    @Reference
    private CategoryService categoryService;

    @RequestMapping("/addRecord")
    private String addRecord(HttpServletRequest request,
                             Map<String,Object> map){
        if(!StringUtils.isEmpty(request.getParameter("msg"))){
            map.put("msg",request.getParameter("msg"));
        }
        return "accounting/addRecord";
    }

    @PostMapping("/addRecord")
    private String addRecord(AcctRecord acctRecord,
                             HttpSession session,
                             RedirectAttributes redirectAttributes){
        User user = (User) session.getAttribute("user");
        acctRecord.setAcct(user.getAcct());
        recordService.addRecord(acctRecord);
        redirectAttributes.addAttribute("msg","记账成功！");
        return "redirect:addRecord";
    }

    @RequestMapping("/allRecords")
    private String allRecords(HttpServletRequest request,
                              RedirectAttributes redirectAttributes){
        if(!StringUtils.isEmpty(request.getParameter("msg"))){
            redirectAttributes.addAttribute("msg",request.getParameter("msg"));
        }
        return "redirect:allRecordsPage?budgetType=&month=&curPage=1&pagesize=10";
    }

    @RequestMapping("/allRecordsPage")
    private String allRecords(@RequestParam String budgetType,
                              @RequestParam String month,
                              @RequestParam int curPage,
                              @RequestParam int pagesize,
                              Map<String,Object> map,
                              HttpServletRequest request,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        map.put("budgetType",budgetType);
        map.put("month",month);
        map.put("count",recordService.getCount(budgetType,month,user.getAcct()));
        map.put("curPage",curPage);
        List<AcctRecord> records = recordService.getRecordsPage(budgetType,month,(curPage-1)*pagesize,pagesize,user.getAcct());
        for (AcctRecord record : records) {
            record.setPay(PayEnum.getDescByName(record.getPay()));
        }
        map.put("records",records);
        if(!StringUtils.isEmpty(request.getParameter("msg"))){
            map.put("msg",request.getParameter("msg"));
        }
        return "accounting/allRecords";
    }

    @RequestMapping("/deleteRecord")
    private String delete(@RequestParam String serialNo){
        recordService.deleteRecord(serialNo);
        return "redirect:allRecords";
    }

    @RequestMapping("/editRecord")
    private String edit(@RequestParam String serialNo,
                        Map<String,Object> map){
        AcctRecord record = recordService.getByPrimaryKey(serialNo);
        map.put("record",record);
        map.put("categories",categoryService.getCategoryByType(record.getBudgetType(),record.getAcct()));
        return "accounting/editRecord";
    }

    @PostMapping(value = "/editRecordDo")
    private String editDo(AcctRecord acctRecord){
        recordService.update(acctRecord);
        return "redirect:allRecords";
    }

    @GetMapping("/getDataForChart")
    @ResponseBody
    private List<AcctRecord> getChartData(@RequestParam String month,
                                          @RequestParam String budgetType,
                                          @RequestParam String chartType,
                                          HttpSession session){
        if(StringUtils.isEmpty(month)){
            Calendar calendar = Calendar.getInstance();
            String monthFormat=(calendar.get(Calendar.MONTH)+1)<10?"0"+(calendar.get(Calendar.MONTH)+1):""+(calendar.get(Calendar.MONTH)+1);
            month = calendar.get(Calendar.YEAR)+"-"+monthFormat;
        }
        User user = (User) session.getAttribute("user");
        return recordService.getChartData(month,budgetType,chartType,user.getAcct());
    }

    @RequestMapping("myBill")
    private String myBill(@RequestParam String billType,
                          HttpSession session,
                          Map<String,Object> map){
        User user = (User) session.getAttribute("user");
        map.put("user",user);
        map.put("billType",billType);
        map.put("bill",recordService.getBill(billType,user.getAcct()));
        return "user/myBill";
    }
}
