package com.edu.nchu.mapper;

import com.edu.nchu.entity.Target;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Target record);

    int insertSelective(Target record);

    Target selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Target record);

    int updateByPrimaryKey(Target record);
}