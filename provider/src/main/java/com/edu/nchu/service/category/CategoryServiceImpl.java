package com.edu.nchu.service.category;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.Category;
import com.edu.nchu.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： CategoryServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.category 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/2 下午 06:13
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryByType(String budgetType) {
        return categoryMapper.selectByType(budgetType);
    }

    @Override
    public List<Category> getAllCategory(int start,int pagesize) {
        return categoryMapper.selectAllCategory(start,pagesize);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int getCount() {
        return categoryMapper.getCount()/10+1;
    }
}
