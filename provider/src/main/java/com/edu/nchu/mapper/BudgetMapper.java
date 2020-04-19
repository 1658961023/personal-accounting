package com.edu.nchu.mapper;

import com.edu.nchu.entity.Budget;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BudgetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Budget record);

    int insertSelective(Budget record);

    Budget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Budget record);

    int updateByPrimaryKey(Budget record);

    Budget selectSelective(String dateType,String startDate);
}