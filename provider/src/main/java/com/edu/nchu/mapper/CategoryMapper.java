package com.edu.nchu.mapper;

import com.edu.nchu.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectByType(String budgetType,String acct);

    List<Category> selectAllCategory(int start,int pagesize,String acct);

    int getCount(String acct);
}