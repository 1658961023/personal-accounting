package com.edu.nchu.mapper;

import com.edu.nchu.entity.AcctRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AcctRecordMapper {
    int deleteByPrimaryKey(String serialNo);

    int insert(AcctRecord record);

    int insertSelective(AcctRecord record);

    AcctRecord selectByPrimaryKey(String serialNo);

    int updateByPrimaryKeySelective(AcctRecord record);

    int updateByPrimaryKey(AcctRecord record);

    List<AcctRecord> selectPage(int start,int pagesize);

    int getCount();
}