package com.edu.nchu.component;

import com.edu.nchu.entity.AcctRecord;
import com.edu.nchu.entity.VirtualAcct;
import com.edu.nchu.entity.VirtualAcctExample;
import com.edu.nchu.entity.enums.BudgetEnum;
import com.edu.nchu.mapper.VirtualAcctMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： VirtualAcctComponent
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.component 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/10 下午 01:26
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Component
public class VirtualAcctComponent {

    @Autowired
    private VirtualAcctMapper virtualAcctMapper;

    public void changeBalanceForAdd(AcctRecord acctRecord){
        VirtualAcctExample virtualAcctExample = new VirtualAcctExample();
        VirtualAcctExample.Criteria criteria = virtualAcctExample.createCriteria();
        criteria.andAcctEqualTo(acctRecord.getAcct());
        criteria.andAcctNameEqualTo(acctRecord.getPay());
        List<VirtualAcct> virtualAccts = virtualAcctMapper.selectByExample(virtualAcctExample);
        VirtualAcct virtualAcct = virtualAccts.get(0);
        String oldBalance = virtualAcct.getBalance();
        if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())) {
            //记账一笔收入，相应账户余额增加
            virtualAcct.setBalance(new BigDecimal(oldBalance).add(new BigDecimal(acctRecord.getAmount())).toString());
        }else {
            //记账一笔支出，相应账户余额减少
            virtualAcct.setBalance(new BigDecimal(oldBalance).subtract(new BigDecimal(acctRecord.getAmount())).toString());
        }
        virtualAcctMapper.updateByPrimaryKeySelective(virtualAcct);
    }

    public void changeBalanceForDelete(AcctRecord acctRecord){
        //删除记账，逻辑和添加记账相反，删除一笔收入记账余额减少，删除一笔支出记账余额增加
        if(BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())){
            acctRecord.setBudgetType(BudgetEnum.EXPEND.getCode());
        }else {
            acctRecord.setBudgetType(BudgetEnum.INCOME.getCode());
        }
        changeBalanceForAdd(acctRecord);
    }

    public void changeBalanceForEdit(AcctRecord acctRecord,AcctRecord old){
        //修改记账，逻辑比较复杂，修改的可以是金额，收支类型和账户，都会对虚拟账户的余额产生影响
        //获取修改后的相应用户的和记账账户对应的账户
        VirtualAcctExample virtualAcctExample = new VirtualAcctExample();
        VirtualAcctExample.Criteria criteria = virtualAcctExample.createCriteria();
        criteria.andAcctEqualTo(acctRecord.getAcct());
        criteria.andAcctNameEqualTo(acctRecord.getPay());
        List<VirtualAcct> virtualAccts = virtualAcctMapper.selectByExample(virtualAcctExample);
        VirtualAcct virtualAcct = virtualAccts.get(0);
        String oldBalance = virtualAcct.getBalance();

        //获取修改前的相应用户的和记账账户对应的账户
        VirtualAcctExample virtualAcctExample2 = new VirtualAcctExample();
        VirtualAcctExample.Criteria criteria2 = virtualAcctExample2.createCriteria();
        criteria2.andAcctEqualTo(old.getAcct());
        criteria2.andAcctNameEqualTo(old.getPay());
        List<VirtualAcct> virtualAccts2 = virtualAcctMapper.selectByExample(virtualAcctExample2);
        VirtualAcct virtualAcct2 = virtualAccts2.get(0);
        String oldBalance2 = virtualAcct2.getBalance();

        if (BudgetEnum.INCOME.getCode().equals(old.getBudgetType())){
            //修改前是一笔收入，修改前对应的账户的余额减少修改前记账的金额
            BigDecimal dBalance = new BigDecimal(old.getAmount());
            virtualAcct2.setBalance(new BigDecimal(oldBalance2).subtract(dBalance).toString());
        }
        if (BudgetEnum.EXPEND.getCode().equals(old.getBudgetType())){
            //修改前是一笔支出，修改前对应的账户的余额增加修改前记账的金额
            BigDecimal dBalance = new BigDecimal(old.getAmount());
            virtualAcct2.setBalance(new BigDecimal(oldBalance2).add(dBalance).toString());
        }
        if (BudgetEnum.INCOME.getCode().equals(acctRecord.getBudgetType())){
            //修改后是一笔收入，修改后的对应的账户余额增加修改后记账的余额
            BigDecimal dBalance = new BigDecimal(acctRecord.getAmount());
            virtualAcct.setBalance(new BigDecimal(oldBalance).add(dBalance).toString());
        }
        if (BudgetEnum.EXPEND.getCode().equals(acctRecord.getBudgetType())){
            //修改后是一笔支出，修改后的对应的账户余额减少修改后记账的余额
            BigDecimal dBalance = new BigDecimal(acctRecord.getAmount());
            virtualAcct.setBalance(new BigDecimal(oldBalance).subtract(dBalance).toString());
        }
        virtualAcctMapper.updateByPrimaryKeySelective(virtualAcct);
        virtualAcctMapper.updateByPrimaryKeySelective(virtualAcct2);
    }
}
