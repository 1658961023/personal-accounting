package com.edu.nchu.mapper;

import com.edu.nchu.entity.AcctRecord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface AcctRecordMapper {
    int deleteByPrimaryKey(String serialNo);

    int insert(AcctRecord record);

    int insertSelective(AcctRecord record);

    AcctRecord selectByPrimaryKey(String serialNo);

    int updateByPrimaryKeySelective(AcctRecord record);

    int updateByPrimaryKey(AcctRecord record);

    List<AcctRecord> selectPage(String budgetType,String month,int start,int pagesize,String acct);

    int getCount(String budgetType,String month,String acct);

    List<AcctRecord> getChartData(@Param("month") String month, @Param("budgetType") String budgetType, @Param("chartType") String chartType,@Param("acct") String acct);

    List<AcctRecord> selectAll(String acct);

    List<AcctRecord> getBill(String acct);
}