package com.edu.nchu.util;

import com.edu.nchu.DTO.CategoryDto;
import com.edu.nchu.entity.Category;
import com.edu.nchu.entity.enums.CategoryEnum;
import com.edu.nchu.entity.enums.MyEnum;
import sun.security.validator.ValidatorException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： MyUtils
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.util 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/3 下午 11:05
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public class MyUtils {
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            //异常 说明包含非数字。
            return false;
        }
        return true;
    }
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    public static List<CategoryDto> transEnumToList(){
        List<CategoryDto> list = new ArrayList<>();

        // 将枚举存放到list里面
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(categoryEnum.getName());
            categoryDto.setBudgetType(categoryEnum.getBudgetType());
            list.add(categoryDto);
        }
        return list;
    }
}
