package com.edu.nchu.component;

import com.edu.nchu.mapper.BudgetMapper;
import com.edu.nchu.mapper.TargetMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/*********************************************************
 @author guoff16201210
  * <p> 文件名称： DynamicScheduleTask
  * <p> 系统名称：个人在线记账系统
  * <p> 模块名称：com.edu.nchu.component 
  * <p> 功能说明：
  * <p> 开发人员：guoff16201210
  * <p> 开发时间：2020/5/9 上午 09:29
  * <p> 修改记录：程序版本	修改日期	修改人员	修改单号	修改说明
 *********************************************************/
@Configuration
@EnableScheduling
public class DynamicScheduleTask implements SchedulingConfigurer {

    /**
     * 记得加上这个哇，这是告诉全世界，你要开始在这类里面使用日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DynamicScheduleTask.class);

    @Mapper
    public interface CronMapper {

        @Select("select cron from t_schedule limit 1")
        String getCron();

        @Update("update t_schedule set today=#{today} where id=1")
        void updateToday(String today);
    }

    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;

    @Autowired
    @SuppressWarnings("all")
    private BudgetMapper budgetMapper;

    @Autowired
    @SuppressWarnings("all")
    private TargetMapper targetMapper;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () ->{
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String today = dateFormat.format(new Date());
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    logger.info("当前月的第一天："+dateFormat.format(calendar.getTime()));
                    if(today.equals(dateFormat.format(calendar.getTime()))){
                        //每个月的一号,重置预算和目标为默认值
                        budgetMapper.updateAllBudgets();
                        targetMapper.updateAllTargets();
                        logger.info("当前日期:"+today+" 为每个月第一天，需要重置预算目标值");
                    }else {
                        logger.info("当前日期:"+today+"不为每个月第一天，不需要重置预算目标值");
                    }
                    cronMapper.updateToday(today);
                    logger.info("执行动态定时任务: " + LocalDateTime.now().toLocalTime());},
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        logger.warn("获取数据库cron表达式为空！");
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

}
