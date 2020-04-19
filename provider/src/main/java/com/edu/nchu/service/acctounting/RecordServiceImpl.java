package com.edu.nchu.service.acctounting;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.Budget;
import com.edu.nchu.entity.Target;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.entity.enums.DateTypeEnum;
import com.edu.nchu.mapper.AcctRecordMapper;
import com.edu.nchu.mapper.BudgetMapper;
import com.edu.nchu.mapper.TargetMapper;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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

    @Autowired
    private BudgetMapper budgetMapper;

    @Autowired
    private TargetMapper targetMapper;

    @Override
    public void addRecord(AcctRecord acctRecord) {
        acctRecord.setSerialNo(String.valueOf(System.currentTimeMillis()));
        //记账时同步更新预算和目标表
        //获取预算和目标信息
        Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
            //记账的收支类型是收入,更新收入目标信息
            BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
            BigDecimal dAmount = totalAmount.subtract(new BigDecimal(t.getTargetAmount()));
            //更新总金额为原总金额加上记账的收入金额
            t.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去目标金额
            t.setdAmount(dAmount.toString());
            //更新数据库
            targetMapper.updateByPrimaryKey(t);
        } else {
            //记账是一笔支出，更新预算信息
            BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
            BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
            //更新总金额为原总金额加上记账支出金额
            b.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去预算金额
            b.setdAmount(dAmount.toString());
            budgetMapper.updateByPrimaryKey(b);
        }
        acctRecordMapper.insert(acctRecord);
    }

    @Override
    public List<AcctRecord> getRecordsPage(int start, int pagesize) {
        return acctRecordMapper.selectPage(start, pagesize);
    }

    @Override
    public void deleteRecord(String serilaNo) {
        //删除前获取原记账信息
        AcctRecord acctRecord = acctRecordMapper.selectByPrimaryKey(serilaNo);
        //记账记录删除时同步更新预算和目标表
        //获取预算和目标信息
        Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
            //删除的记账的收支类型是收入,更新收入目标信息
            BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).subtract(new BigDecimal(acctRecord.getAmount()));
            BigDecimal dAmount = totalAmount.subtract(new BigDecimal(t.getTargetAmount()));
            //更新总金额为原总金额减去记账的收入金额
            t.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去目标金额
            t.setdAmount(dAmount.toString());
            //更新数据库
            targetMapper.updateByPrimaryKey(t);
        } else {
            //记账是一笔支出，更新预算信息
            BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).subtract(new BigDecimal(acctRecord.getAmount()));
            BigDecimal dAmount = totalAmount.subtract(new BigDecimal(b.getBudgetAmount()));
            //更新总金额为原总金额减去记账支出金额
            b.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去预算金额
            b.setdAmount(dAmount.toString());
            budgetMapper.updateByPrimaryKey(b);
        }
        acctRecordMapper.deleteByPrimaryKey(serilaNo);
    }

    @Override
    public AcctRecord getByPrimaryKey(String serialNo) {
        return acctRecordMapper.selectByPrimaryKey(serialNo);
    }

    @Override
    public void update(AcctRecord acctRecord) {
        AcctRecord old = acctRecordMapper.selectByPrimaryKey(acctRecord.getSerialNo());
        //记账记录金额发生变化，同步更新预算和收入目标信息
        Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getDate());
        if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
            //记账的收支类型是收入,更新收入目标信息
            BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
            BigDecimal dAmount = totalAmount.subtract(new BigDecimal(t.getTargetAmount()));
            //更新总金额为原总金额加上记账的收入金额
            t.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去目标金额
            t.setdAmount(dAmount.toString());
            //更新数据库
            targetMapper.updateByPrimaryKey(t);
        } else {
            //记账是一笔支出，更新预算信息
            BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
            BigDecimal dAmount = totalAmount.subtract(new BigDecimal(b.getBudgetAmount()));
            //更新总金额为原总金额加上记账支出金额
            b.setTotalAmount(totalAmount.toString());
            //更新差值为新的总金额减去预算金额
            b.setdAmount(dAmount.toString());
            budgetMapper.updateByPrimaryKey(b);
        }
        acctRecordMapper.updateByPrimaryKeySelective(acctRecord);
    }

    @Override
    public int getCount() {
        return acctRecordMapper.getCount() / 10 + 1;
    }

    @Override
    public List<AcctRecord> getChartData(String month, String budgetType, String chartType) {
        List<AcctRecord> list = acctRecordMapper.getChartData(month, budgetType, chartType);
        return list;
    }

    @Override
    public List<AcctRecord> selectAll() {
        return acctRecordMapper.selectAll();
    }

}
