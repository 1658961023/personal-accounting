package com.edu.nchu.service.budgetTarget;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.entity.Budget;
import com.edu.nchu.entity.Target;
import com.edu.nchu.mapper.BudgetMapper;
import com.edu.nchu.mapper.TargetMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： BudgetTargetServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.budgetTarget 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/16 下午 01:31
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class BudgetTargetServiceImpl implements BudgetTargetService{

    @Autowired
    private BudgetMapper budgetMapper;

    @Autowired
    private TargetMapper targetMapper;

    @Override
    public Budget getBudget(String dateType,String startDate) {
        return budgetMapper.selectSelective(dateType, startDate);
    }

    @Override
    public Target getTarget(String dateType,String startDate) {
        return targetMapper.selectSelective(dateType, startDate);
    }

    @Override
    public void editBudget(String budgetAmount, String dateType,String month) {
        Budget budget = budgetMapper.selectSelective(dateType,month+"-01");
        budget.setBudgetAmount(budgetAmount);
        budgetMapper.updateByPrimaryKey(budget);
    }

    @Override
    public void editTarget(String targetAmount, String dateType,String month) {
        Target target = targetMapper.selectSelective(dateType,month+"-01");
        target.setTargetAmount(targetAmount);
        targetMapper.updateByPrimaryKey(target);
    }
}
