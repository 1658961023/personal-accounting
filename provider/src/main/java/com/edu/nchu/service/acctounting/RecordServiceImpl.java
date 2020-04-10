package com.edu.nchu.service.acctounting;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.mapper.AcctRecordMapper;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RecordServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.acctounting 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/27 下午 07:34
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private AcctRecordMapper acctRecordMapper;

    @Override
    public void addRecord(AcctRecord acctRecord) {
        acctRecord.setSerialNo(String.valueOf(System.currentTimeMillis()));
        acctRecordMapper.insert(acctRecord);
    }

    @Override
    public List<AcctRecord> getRecordsPage(int start,int pagesize) {
        return acctRecordMapper.selectPage(start,pagesize);
    }

    @Override
    public void deleteRecord(String serilaNo) {
        acctRecordMapper.deleteByPrimaryKey(serilaNo);
    }

    @Override
    public AcctRecord getByPrimaryKey(String serialNo) {
        return acctRecordMapper.selectByPrimaryKey(serialNo);
    }

    @Override
    public void update(AcctRecord acctRecord) {
        acctRecordMapper.updateByPrimaryKeySelective(acctRecord);
    }

    @Override
    public int getCount() {
        return acctRecordMapper.getCount()/10+1;
    }

}
