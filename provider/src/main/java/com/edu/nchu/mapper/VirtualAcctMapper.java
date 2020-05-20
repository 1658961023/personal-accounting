package com.edu.nchu.mapper;

import com.edu.nchu.entity.VirtualAcct;
import com.edu.nchu.entity.VirtualAcctExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VirtualAcctMapper {
    int countByExample(VirtualAcctExample example);

    int deleteByExample(VirtualAcctExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VirtualAcct record);

    int insertSelective(VirtualAcct record);

    List<VirtualAcct> selectByExample(VirtualAcctExample example);

    VirtualAcct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VirtualAcct record, @Param("example") VirtualAcctExample example);

    int updateByExample(@Param("record") VirtualAcct record, @Param("example") VirtualAcctExample example);

    int updateByPrimaryKeySelective(VirtualAcct record);

    int updateByPrimaryKey(VirtualAcct record);
}