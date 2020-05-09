package com.edu.nchu.controller.acctounting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.edu.nchu.component.ExcelListener;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.Category;
import com.edu.nchu.entity.User;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.service.accounting.RecordService;
import com.edu.nchu.service.category.CategoryService;
import com.edu.nchu.util.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    @Reference
    private CategoryService categoryService;

    @RequestMapping("/export")
    public String exporExcel(HttpServletResponse response,
                             HttpSession session) throws IOException {
        //todo 是否分页导出或全部导出
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("记账流水", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            User user = (User) session.getAttribute("user");
            List<AcctRecord> records = recordService.selectAll(user.getAcct());
            for (AcctRecord record : records) {
                record.setBudgetType(BudgetEnum.INCOME.getCode().equals(record.getBudgetType())?"收入":"支出");
            }
            EasyExcel.write(response.getOutputStream(), AcctRecord.class).sheet("记账流水").doWrite(records);
            writer.finish();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:allRecords";
    }

    @RequestMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) throws IOException {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")+1);
        if(!StringUtils.isEmpty(file.getOriginalFilename())){
            if(!"xls".equals(suffix) && !"xlsx".equals(suffix)){
                redirectAttributes.addAttribute("msg","上传文件格式不正确，仅支持.xls,xlsx");
                return "redirect:allRecords";
            }
        }
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, null, listener).headRowNumber(0).build();
        excelReader.read();
        //获取数据
        List<List<String>> list = listener.getDatas();
        User user = (User) session.getAttribute("user");

        //转换数据类型,并插入到数据库
        for (int i=1;i<list.size();i++) {
            AcctRecord record = new AcctRecord();
            record.setAcct(user.getAcct());
            record.setBudgetType(list.get(i).get(0));
            record.setCategory(list.get(i).get(1));
            record.setAmount((list.get(i).get(2)));
            record.setDate(list.get(i).get(3));
            record.setSummary(list.get(i).get(4));
            if(!"success".equals(checkRecord(record))){
                redirectAttributes.addAttribute("msg",checkRecord(record));
                return "redirect:allRecords";
            }
            recordService.addRecord(record);
        }
        excelReader.finish();
        return "redirect:allRecords";
    }

    private String checkRecord(AcctRecord acctRecord){
        List<Category> categories = categoryService.getCategoryByType(acctRecord.getBudgetType(),acctRecord.getAcct());
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        if(!BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType()) && !BudgetEnum.EXPEND.getCode().equals(acctRecord.getBudgetType())){
            return "收支类型格式不正确，0:收入，1:支出";
        }else if (!names.contains(acctRecord.getCategory())){
            return "分类不存在";
        }else if(!MyUtils.isNumeric(acctRecord.getAmount())){
            return "金额必须为数字";
        }else if(!MyUtils.isValidDate(acctRecord.getDate())){
            return "日期不正确，格式为yyyy-MM-dd";
        }else {
            return "success";
        }
    }
}
