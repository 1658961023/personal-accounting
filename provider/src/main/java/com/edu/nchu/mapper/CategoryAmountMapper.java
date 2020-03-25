package com.edu.nchu.mapper;

import com.edu.nchu.entity.CategoryAmount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryAmountMapper {
    int insert(CategoryAmount record);

    int insertSelective(CategoryAmount record);
}