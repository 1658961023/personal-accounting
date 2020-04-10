package com.edu.nchu.controller.acctounting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.edu.nchu.component.ExcelListener;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： ExcelController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.acctounting 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/9 上午 11:20
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class ExcelController {

    @Reference
    private RecordService recordService;

    @RequestMapping("/export")
    public String exporExcel(HttpServletResponse response) throws IOException {
        //todo 是否分页导出或全部导出
//        ExcelWriter writer = null;
//        OutputStream outputStream = response.getOutputStream();
//        try {
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf-8");
//            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//            String fileName = URLEncoder.encode("记账流水", "UTF-8");
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            List<AcctRecord> records = recordService.getRecordsPage();
//            for (AcctRecord record : records) {
//                record.setBudgetType(BudgetEnum.INCOME.getCode().equals(record.getBudgetType())?"收入":"支出");
//            }
//            EasyExcel.write(response.getOutputStream(), AcctRecord.class).sheet("记账记录").doWrite(records);
//            writer.finish();
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                response.getOutputStream().close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return "redirect:allRecords";
    }

    @RequestMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, null, listener).headRowNumber(0).build();
        excelReader.read();
        //获取数据
        List<List<String>> list = listener.getDatas();

        //转换数据类型,并插入到数据库
        for (int i=1;i<list.size();i++) {
            AcctRecord record = new AcctRecord();
            record.setBudgetType(list.get(i).get(0));
            record.setCategory(list.get(i).get(1));
            record.setAmount(Integer.valueOf(list.get(i).get(2)));
            record.setDate(list.get(i).get(3));
            record.setSummary(list.get(i).get(4));
            recordService.addRecord(record);
        }
        excelReader.finish();
        return "redirect:allRecords";
    }
}
