package com.edu.nchu.controller.acctounting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/addRecord")
    private String addRecord(){
        return "accounting/addRecord";
    }

    @PostMapping("/addRecord")
    private String addRecord(AcctRecord acctRecord){
        recordService.addRecord(acctRecord);
        return "index";
    }

    @RequestMapping("/allRecords")
    private String allRecords(Map<String,Object> map){
        return "redirect:allRecordsPage?curPage=1&pagesize=10";
    }

    @RequestMapping("/allRecordsPage")
    private String allRecords(@RequestParam int curPage,
                              @RequestParam int pagesize,
                              Map<String,Object> map){
        map.put("count",recordService.getCount());
        map.put("curPage",curPage);
        map.put("records",recordService.getRecordsPage((curPage-1)*pagesize,pagesize));
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
        map.put("record",recordService.getByPrimaryKey(serialNo));
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
                                          @RequestParam String chartType){
        return recordService.getChartData(month,budgetType,chartType);
    }
}
