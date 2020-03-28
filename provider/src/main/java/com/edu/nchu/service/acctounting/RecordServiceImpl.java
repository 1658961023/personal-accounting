package com.edu.nchu.service.acctounting;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.mapper.AcctRecordMapper;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public String addRecord(AcctRecord acctRecord) {
        acctRecordMapper.insert(acctRecord);
        return "redirect:index";
    }
}
