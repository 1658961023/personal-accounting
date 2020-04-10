package com.edu.nchu.service.accounting;

import com.edu.nchu.entity.AcctRecord;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RecordService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.accounting 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/27 下午 07:33
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface RecordService {

    void addRecord(AcctRecord acctRecord);

    List<AcctRecord> getRecordsPage(int start,int pagesize);

    void deleteRecord(String serialNo);

    AcctRecord getByPrimaryKey(String serialNo);

    void update(AcctRecord acctRecord);

    int getCount();
}

