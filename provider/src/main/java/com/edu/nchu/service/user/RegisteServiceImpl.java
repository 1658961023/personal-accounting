package com.edu.nchu.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.edu.nchu.DTO.CategoryDto;
import com.edu.nchu.entity.*;
import com.edu.nchu.entity.enums.DateTypeEnum;
import com.edu.nchu.mapper.*;
import com.edu.nchu.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： RegisteServiceImpl
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.service.user
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/3/24 下午 06:56
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Service
public class RegisteServiceImpl implements RegisteService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BudgetMapper budgetMapper;

    @Autowired
    private TargetMapper targetMapper;

    @Autowired
    private VirtualAcctMapper virtualAcctMapper;

    @Override
    public String register(String acct, String password, String nickname) {
        if (ObjectUtils.isEmpty(userMapper.selectByAcct(acct))) {
            //账号不重复，可以使用
            User user = new User();
            user.setAcct(acct);
            user.setPassword(password);
            user.setNickname(nickname);
            userMapper.insertSelective(user);
            //新建系统默认分类
            List<CategoryDto> categories = MyUtils.transCategoryEnumToList();
            for (CategoryDto category : categories) {
                Category category1 = new Category();
                category1.setName(category.getName());
                category1.setBudgetType(category.getBudgetType());
                category1.setAcct(acct);
                categoryMapper.insert(category1);
            }
            //为新用户创建新的预算和目标，默认每月3000
            //新建每月预算
            Budget budget = new Budget();
            budget.setAcct(acct);
            budget.setBudgetAmount("3000");
            budget.setdAmount("3000");
            budget.setTotalAmount("0");
            budget.setDateType(DateTypeEnum.MONTH.getCode());
            budgetMapper.insert(budget);
            //新建每月目标
            Target target = new Target();
            target.setAcct(acct);
            target.setTargetAmount("3000");
            target.setdAmount("3000");
            target.setTotalAmount("0");
            target.setDateType(DateTypeEnum.MONTH.getCode());
            targetMapper.insert(target);
            //新用户创建虚拟五个账户,余额都为0
            List<String> virAccts = MyUtils.transPayEnumToList();
            for (String virAcct : virAccts) {
                VirtualAcct virtualAcct = new VirtualAcct();
                virtualAcct.setAcct(acct);
                virtualAcct.setAcctName(virAcct);
                virtualAcct.setIncome("0");
                virtualAcct.setExpend("0");
                virtualAcct.setBalance("0");
                virtualAcctMapper.insert(virtualAcct);
            }
            return "redirect:login";
        }
        //账号重复，无法注册
        return "register";
    }
}
