package com.edu.nchu.controller.acctounting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
public class accountingController {

    @Reference
    private RecordService recordService;

    @RequestMapping("/addRecord")
    private String addRecord(){
        return "accounting/addRecord";
    }

    @PostMapping("/addRecord")
    private String addRecord(AcctRecord acctRecord){
        acctRecord.setSerialNo(String.valueOf(System.currentTimeMillis()));
        recordService.addRecord(acctRecord);
        return "index";
    }
}
