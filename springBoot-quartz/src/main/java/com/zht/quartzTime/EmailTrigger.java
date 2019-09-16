package com.zht.quartzTime;

import com.zht.quartzListener.MailJobListener;
import com.zht.quartzTimeJob.EmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zht
 * @create 2019-09-05 9:19
 */
public class EmailTrigger {

    @Autowired
    private  Scheduler scheduler;  //创建调度器

    public  void scheduler() throws SchedulerException, InterruptedException {

        //定义一个触发器
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义名称和所属的组
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1) //每隔2秒执行一次
                        .withRepeatCount(2)) //总共执行3次(第一次执行不基数)
                .build();

        //定义一个JobDetail
        JobDetail job = JobBuilder.newJob(EmailJob.class) //指定干活的类MailJob
                .withIdentity("mailjob", "mailgroup") //定义任务名称和分组
                .usingJobData("email", "admin@10086.com") //定义属性，增加属性 key 和 值，传递给任务
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        //Thread.sleep(20000);
        //scheduler.shutdown(true);
    }
}
