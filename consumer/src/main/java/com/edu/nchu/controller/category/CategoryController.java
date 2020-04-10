package com.edu.nchu.controller.category;

import com.alibaba.dubbo.config.annotation.Reference;
import com.edu.nchu.entity.Category;
import com.edu.nchu.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： categoryController
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.controller.category 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/2 下午 06:10
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Controller
public class CategoryController {

    @Reference
    private CategoryService categoryService;

    @RequestMapping("/getCategoryByType")
    @ResponseBody
    List<Category> getCategoryByType(@RequestParam String budgetType){
        return categoryService.getCategoryByType(budgetType);
    }

    @RequestMapping("/allCategoryPage")
    String getAllCategory(@RequestParam int curPage,
                          @RequestParam int pagesize,
                          Map<String,Object> map){
        map.put("categories",categoryService.getAllCategory((curPage-1)*pagesize,pagesize));
        map.put("count",categoryService.getCount());
        map.put("curPage",curPage);
        return "category/allCategory";
    }

    @RequestMapping("/allCategory")
    String getAllCategory(Map<String,Object> map){
        return "redirect:allCategoryPage?curPage=1&pagesize=10";
    }

    @RequestMapping("/addCategory")
    String addCategory(Category category){
        categoryService.addCategory(category);
        return "redirect:allCategory";
    }

    @RequestMapping("/deleteCategory")
    String deleteCategory(@RequestParam int id){
        categoryService.deleteCategory(id);
        return "redirect:allCategory";
    }
}
