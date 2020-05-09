package com.edu.nchu.service.budgetTarget;

import com.edu.nchu.entity.Budget;
import com.edu.nchu.entity.Target;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： BudgetTargetService
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.budgetTarget 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/4/16 下午 12:29
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
public interface BudgetTargetService {

    Budget getBudget(String dateType,String acct);

    Target getTarget(String dateType,String acct);

    void editBudget(String budgetAmount, String dateType, String acct);

    void editTarget(String targetAmount, String dateType, String acct);
}

