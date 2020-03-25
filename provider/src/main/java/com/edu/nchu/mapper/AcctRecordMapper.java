package com.edu.nchu.mapper;

import com.edu.nchu.entity.AcctRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcctRecordMapper {
    int deleteByPrimaryKey(String serialNo);

    int insert(AcctRecord record);

    int insertSelective(AcctRecord record);

    AcctRecord selectByPrimaryKey(String serialNo);

    int updateByPrimaryKeySelective(AcctRecord record);

    int updateByPrimaryKey(AcctRecord record);
}