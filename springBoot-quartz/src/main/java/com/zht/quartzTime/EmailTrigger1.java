package com.zht.quartzTime;

import com.zht.quartzTimeJob.EmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zht
 * @create 2019-09-05 9:19
 */
public class EmailTrigger1 {

    //创建调度器
    @Autowired
    private Scheduler scheduler;

    public  void scheduler() throws SchedulerException, InterruptedException {
        //定义一个触发器
        Trigger trigger = newTrigger().withIdentity("trigger2", "group1") //定义名称（唯一）和所属的组
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1) //每隔2秒执行一次
                        .withRepeatCount(3)) //总共执行11次(第一次执行不基数)
                .build();
        //定义一个JobDetail
        JobDetail job = JobBuilder.newJob(EmailJob.class) //指定干活的类MailJob
                .withIdentity("mailjob1", "mailgroup") //定义任务名称（唯一）和分组
                .build();
        job.getJobDataMap().put("email", "1669638693@qq.com");//对 EmailJob 添加 数据任务
        //调度加入这个job
        scheduler.scheduleJob(job, trigger);
        //启动
        scheduler.start();
        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
//        Thread.sleep(20000);
//        scheduler.shutdown(true);
    }
}
