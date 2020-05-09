package com.edu.nchu.service.category;

import com.edu.nchu.entity.Category;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： CatrgoryService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.category 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/2 下午 06:11
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface CategoryService {

    List<Category> getCategoryByType(String budgetType,String acct);

    List<Category> getAllCategory(int start,int pagesize,String acct);

    void addCategory(Category category);

    void deleteCategory(int id);

    int getCount(String acct);
}
