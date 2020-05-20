package com.edu.nchu.service.acctounting;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.DTO.BillDto;
import com.edu.nchu.component.VirtualAcctComponent;
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
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private VirtualAcctComponent virtualAcctComponent;

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
        //更新账户余额
        virtualAcctComponent.changeBalanceForAdd(acctRecord);
        acctRecordMapper.insert(acctRecord);
    }

    @Override
    public List<AcctRecord> getRecordsPage(String budgetType, String month, int start, int pagesize, String acct) {
        return acctRecordMapper.selectPage(budgetType, month, start, pagesize, acct);
    }

    @Override
    public void deleteRecord(String serilaNo) {
        //删除前获取原记账信息
        AcctRecord acctRecord = acctRecordMapper.selectByPrimaryKey(serilaNo);
        //是本月的记账则更新预算和目标
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
        //更新账户余额
        virtualAcctComponent.changeBalanceForDelete(acctRecord);
        acctRecordMapper.deleteByPrimaryKey(serilaNo);
    }

    @Override
    public AcctRecord getByPrimaryKey(String serialNo) {
        return acctRecordMapper.selectByPrimaryKey(serialNo);
    }

    @Override
    public void update(AcctRecord acctRecord) {
        //获取修改前的记账
        AcctRecord old = acctRecordMapper.selectByPrimaryKey(acctRecord.getSerialNo());
        //记账记录金额发生变化，同步更新预算和收入目标信息
        //是本月的记账则更新
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String now = cal.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : "" + month);
        Target t = targetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
        Budget b = budgetMapper.selectSelective(DateTypeEnum.MONTH.getCode(), acctRecord.getAcct());
        if (now.equals(old.getDate().substring(0, 7)) && now.equals(acctRecord.getDate().substring(0, 7))) {
            //本月的记账改为本月的记账
            if (acctRecord.getBudgetType().equals(old.getBudgetType())) {
                //修改的记账的收支类型和原类型相同
                if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                    //记账的收支类型是收入,更新收入目标信息，比如5月份的一笔1000元收入修改为5月份的500收入
                    BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
                    BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                    //更新总金额为原总金额加上记账的收入金额
                    t.setTotalAmount(totalAmount.toString());
                    //更新差值为目标金额减去新的总金额
                    t.setdAmount(dAmount.toString());
                } else {
                    //记账是一笔支出，更新预算信息，比如5月份的一笔1000元支出修改为5月份的500支出
                    BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()).subtract(new BigDecimal(old.getAmount())));
                    BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                    //更新总金额为原总金额加上记账支出金额
                    b.setTotalAmount(totalAmount.toString());
                    //更新差值为预算金额减去新的总金额
                    b.setdAmount(dAmount.toString());
                }
            } else if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                //修改的记账为支出改成收入
                //更新预算总金额为原金额减去原记账金额
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).subtract(new BigDecimal(old.getAmount()));
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                b.setdAmount(dAmount.toString());
                //更新目标总金额为原金额加上新记账金额
                BigDecimal totalAmont2 = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                t.setTotalAmount(totalAmont2.toString());
                //更新差值为目标金额减去新总金额
                BigDecimal dAmount2 = new BigDecimal(t.getTargetAmount()).subtract(totalAmont2);
                t.setdAmount(dAmount2.toString());
            } else {
                //修改的记账为收入改成支出
                //更新预算总金额为原金额加上新记账金额
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                b.setdAmount(dAmount.toString());
                //更新目标总金额为原金额减去原记账金额
                BigDecimal totalAmount2 = new BigDecimal(t.getTotalAmount()).subtract(new BigDecimal(old.getAmount()));
                t.setTotalAmount(totalAmount2.toString());
                //更新差值为目标金额减去新总金额
                BigDecimal dAmount2 = new BigDecimal(t.getTargetAmount()).subtract(totalAmount2);
                t.setdAmount(dAmount2.toString());
            }
        } else if (now.equals(acctRecord.getDate().substring(0, 7))) {
            //其他月份的记账改为本月的记账，相当于新增了一笔记账
            if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
                //记账的收支类型是收入,更新收入目标信息
                BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账的收入金额
                t.setTotalAmount(totalAmount.toString());
                //更新差值为目标金额减去新的总金额
                t.setdAmount(dAmount.toString());
            } else {
                //记账是一笔支出，更新预算信息
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).add(new BigDecimal(acctRecord.getAmount()));
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                //更新总金额为原总金额加上记账支出金额
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                b.setdAmount(dAmount.toString());
            }
        } else {
            //本月的记账改成其他月份记账，相当于删除了一笔本月记账
            if (BudgetEnum.INCOME.getCode().equals(old.getBudgetType())) {
                //删除的记账的收支类型是收入,更新收入目标信息
                BigDecimal totalAmount = new BigDecimal(t.getTotalAmount()).subtract(new BigDecimal(old.getAmount()));
                BigDecimal dAmount = new BigDecimal(t.getTargetAmount()).subtract(totalAmount);
                //更新总金额为原总金额减去记账的收入金额
                t.setTotalAmount(totalAmount.toString());
                //更新差值为目标金额减去新的总金额
                t.setdAmount(dAmount.toString());
            } else {
                //记账是一笔支出，更新预算信息
                BigDecimal totalAmount = new BigDecimal(b.getTotalAmount()).subtract(new BigDecimal(old.getAmount()));
                BigDecimal dAmount = new BigDecimal(b.getBudgetAmount()).subtract(totalAmount);
                //更新总金额为原总金额减去记账支出金额
                b.setTotalAmount(totalAmount.toString());
                //更新差值为预算金额减去新的总金额
                b.setdAmount(dAmount.toString());
            }
        }
        //更新数据库预算和目标
        targetMapper.updateByPrimaryKey(t);
        budgetMapper.updateByPrimaryKey(b);
        //更新账户余额
        virtualAcctComponent.changeBalanceForEdit(acctRecord, old);
        acctRecordMapper.updateByPrimaryKeySelective(acctRecord);
    }

    @Override
    public int getCount(String budgetType, String month, String acct) {
        Integer count = (int) Math.ceil(acctRecordMapper.getCount(budgetType, month, acct) * 1.0 / 10);
        return count > 0 ? count : 1;
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
                billDto.setMonth(year + "-" + (i < 10 ? "0" + i : "" + i));
                for (AcctRecord acctrecord : list) {
                    if (acctrecord.getDate().equals(billDto.getMonth())) {
                        if (BudgetEnum.INCOME.getCode().equals(acctrecord.getBudgetType())) {
                            billDto.setIncome(acctrecord.getAmount());
                        }
                        if (BudgetEnum.EXPEND.getCode().equals(acctrecord.getBudgetType())) {
                            billDto.setExpend(acctrecord.getAmount());
                        }
                        billDto.setIncome(StringUtils.isEmpty(billDto.getIncome()) ? "0" : billDto.getIncome());
                        billDto.setExpend(StringUtils.isEmpty(billDto.getExpend()) ? "0" : billDto.getExpend());
                        String balance = new BigDecimal(billDto.getIncome()).subtract(new BigDecimal(billDto.getExpend())).toString();
                        billDto.setBalance(balance);
                        recorded = true;
                    }
                }
                if (!recorded) {
                    billDto.setIncome("0");
                    billDto.setExpend("0");
                    billDto.setBalance("0");
                }
                bill.add(billDto);
            }
        } else if (BillTypeEnum.ALL.getCode().equals(billType)) {
            //获取所有有记账记录月份的账单
            Map<String, BillDto> map = new HashMap<>();
            for (AcctRecord acctrecord : list) {
                if (map.containsKey(acctrecord.getDate())) {
                    //map中已经有相应月份的账单记录
                    //先获取已有的记录
                    BillDto billDto = map.get(acctrecord.getDate());
                    if (BudgetEnum.INCOME.getCode().equals(acctrecord.getBudgetType())) {
                        billDto.setIncome(acctrecord.getAmount());
                    }
                    if (BudgetEnum.EXPEND.getCode().equals(acctrecord.getBudgetType())) {
                        billDto.setExpend(acctrecord.getAmount());
                    }
                    //更新后的账单放回去
                    map.put(acctrecord.getDate(), billDto);
                } else {
                    //map中还没有相应月份的账单，直接新建
                    BillDto billDto = new BillDto();
                    billDto.setMonth(acctrecord.getDate());
                    if (BudgetEnum.INCOME.getCode().equals(acctrecord.getBudgetType())) {
                        billDto.setIncome(acctrecord.getAmount());
                    }
                    if (BudgetEnum.EXPEND.getCode().equals(acctrecord.getBudgetType())) {
                        billDto.setExpend(acctrecord.getAmount());
                    }
                    //新建的账单放进去
                    map.put(acctrecord.getDate(), billDto);
                }
            }
            for (String key : map.keySet()) {
                BillDto billDto = map.get(key);
                billDto.setIncome(StringUtils.isEmpty(billDto.getIncome()) ? "0" : billDto.getIncome());
                billDto.setExpend(StringUtils.isEmpty(billDto.getExpend()) ? "0" : billDto.getExpend());
                String balance = new BigDecimal(billDto.getIncome()).subtract(new BigDecimal(billDto.getExpend())).toString();
                billDto.setBalance(balance);
                bill.add(billDto);
            }
        }
        return bill;
    }

}