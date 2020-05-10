package com.edu.nchu.service.acctounting;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.DTO.BillDto;
import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.Budget;
import com.edu.nchu.entity.Target;
import com.edu.nchu.entity.enums.BillTypeEnum;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.entity.enums.DateTypeEnum;
import com.edu.nchu.mapper.AcctRecordMapper;
import com.edu.nchu.mapper.BudgetMapper;
import com.edu.nchu.mapper.TargetMapper;
import com.edu.nchu.service.accounting.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
        //是本月的记账则更新
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String now = cal.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : "" + month);
        if (now.equals(acctRecord.getDate().substring(0, 7))) {
            //记账时同步更新预算和目标表
            //获取预算和目标信息
            Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                //记账的收支类型是收入,更新收入目标信息
                BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账的收入金额
                t.setTotalAmount(totalAmount.toString());
                //更新差值为目标金额减去新的总金额
                t.setdAmount(dAmount.toString());
                //更新数据库
                targetMapper.updateByPrimaryKey(t);
            } else {
                //记账是一笔支出，更新预算信息
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账支出金额
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                b.setdAmount(dAmount.toString());
                budgetMapper.updateByPrimaryKey(b);
            }
        }
        acctRecordMapper.insert(acctRecord);
    }

    @Override
    public List<AcctRecord> getRecordsPage(int start, int pagesize, String acct) {
        return acctRecordMapper.selectPage(start, pagesize, acct);
    }

    @Override
    public void deleteRecord(String serilaNo) {
        //删除前获取原记账信息
        AcctRecord acctRecord = acctRecordMapper.selectByPrimaryKey(serilaNo);
        //是本月的记账则更新
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String now = cal.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : "" + month);
        if (now.equals(acctRecord.getDate().substring(0, 7))) {
            //记账记录删除时同步更新预算和目标表
            //获取预算和目标信息
            Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                //删除的记账的收支类型是收入,更新收入目标信息
                BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).subtract(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                //更新总金额为原总金额减去记账的收入金额
                t.setTotalAmount(totalAmount.toString());
                //更新差值为目标金额减去新的总金额
                t.setdAmount(dAmount.toString());
                //更新数据库
                targetMapper.updateByPrimaryKey(t);
            } else {
                //记账是一笔支出，更新预算信息
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).subtract(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                //更新总金额为原总金额减去记账支出金额
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                b.setdAmount(dAmount.toString());
                budgetMapper.updateByPrimaryKey(b);
            }
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
        //是本月的记账则更新
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String now = cal.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : "" + month);
        if (now.equals(old.getDate().substring(0, 7))) {
            Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
            if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                //记账的收支类型是收入,更新收入目标信息
                BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
                BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账的收入金额
                t.setTotalAmount(totalAmount.toString());
                //更新差值为目标金额减去新的总金额
                t.setdAmount(dAmount.toString());
                //更新数据库
                targetMapper.updateByPrimaryKey(t);
            } else {
                //记账是一笔支出，更新预算信息
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账支出金额
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                b.setdAmount(dAmount.toString());
                budgetMapper.updateByPrimaryKey(b);
            }
        }
        acctRecordMapper.updateByPrimaryKeySelective(acctRecord);
    }

    @Override
    public int getCount(String acct) {
        return acctRecordMapper.getCount(acct) / 10 + 1;
    }

    @Override
    public List<AcctRecord> getChartData(String month, String budgetType, String chartType, String acct) {
        List<AcctRecord> list = acctRecordMapper.getChartData(month, budgetType, chartType, acct);
        return list;
    }

    @Override
    public List<AcctRecord> selectAll(String acct) {
        return acctRecordMapper.selectAll(acct);
    }

    @Override
    public List<BillDto> getBill(String billType, String acct) {
        List<AcctRecord> list = acctRecordMapper.getBill(acct);
        List<BillDto> bill = new ArrayList<>();
        if (BillTypeEnum.THIS_YEAR.getCode().equals(billType)) {
            //仅获取本年前几个月的账单
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            for (int i = 1; i < month; i++) {
                BillDto billDto = new BillDto();
                boolean recorded = false;
                billDto.setMonth(year+"-"+(i < 10 ? "0" + i : "" + i));
                for (AcctRecord acctrecord : list) {
                    if(acctrecord.getDate().equals(billDto.getMonth())){
                        billDto.setIncome(BudgetEnum.INCOME.getCode().equals(acctrecord.getBudgetType())?acctrecord.getAmount():"0");
                        billDto.setExpend(BudgetEnum.EXPEND.getCode().equals(acctrecord.getBudgetType())?acctrecord.getAmount():"0");
                        String balance = new BigDecimal(billDto.getIncome()).subtract(new BigDecimal(billDto.getExpend())).toString();
                        billDto.setBalance(balance);
                        recorded = true;
                    }
                }
                if(!recorded){
                    billDto.setIncome("0");
                    billDto.setExpend("0");
                    billDto.setBalance("0");
                }
                bill.add(billDto);
            }
        }else if(BillTypeEnum.ALL.getCode().equals(billType)){
            //获取所有有记账记录月份的账单
            for (AcctRecord acctrecord : list) {
                BillDto billDto = new BillDto();
                billDto.setMonth(acctrecord.getDate());
                billDto.setIncome(BudgetEnum.INCOME.getCode().equals(acctrecord.getBudgetType())?acctrecord.getAmount():"0");
                billDto.setExpend(BudgetEnum.EXPEND.getCode().equals(acctrecord.getBudgetType())?acctrecord.getAmount():"0");
                String balance = new BigDecimal(billDto.getIncome()).subtract(new BigDecimal(billDto.getExpend())).toString();
                billDto.setBalance(balance);
                bill.add(billDto);
            }
        }
        return bill;
    }

}
