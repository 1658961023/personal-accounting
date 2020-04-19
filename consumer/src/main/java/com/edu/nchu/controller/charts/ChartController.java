package com.edu.nchu.controller.charts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： ChartController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.charts 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/13 下午 04:52
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class ChartController {

    @RequestMapping("/charts")
    private String showCharts(){
        return "charts/charts";
    }
}
