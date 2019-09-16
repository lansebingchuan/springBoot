package com.zht.quartzTimeTest;

import com.zht.quartzListener.MailJobListener;
import com.zht.quartzTimeJob.EmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zht
 * @create 2019-09-05 9:19
 */
public class EmailTrigger {

    public static void main(String[] args) throws Exception {
        try {
            scheduler();
        } catch (ObjectAlreadyExistsException e) {
            System.err.println("发现任务已经在数据库存在了，直接从数据库里运行:"+ e.getMessage());
            // TODO Auto-generated catch block
            resumeJobFromDatabase();//继续执行所有的方法
        }

    }

    public static void resumeJobFromDatabase() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        // 等待200秒，让前面的任务都执行完了之后，再关闭调度器
        //Thread.sleep(6000);
        //scheduler.shutdown(true);
    }

    public static void scheduler() throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //定义一个触发器
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义名称和所属的组
                .startNow()
                .withSchedule(simpleSchedule()  //适用于什么时间后开始，然后重复，或者重复次数
                        .withIntervalInSeconds(2) //每隔2秒执行一次
                        .withRepeatCount(5)  //定时次数
                 //       .repeatForever() //永久重复
                ) //总共执行3次(第一次执行不基数)
                .build();

        //定义一个JobDetail
        JobDetail job = JobBuilder.newJob(EmailJob.class) //指定干活的类MailJob
                .withIdentity("mailjob", "mailgroup") //定义任务名称(key)和分组
                .usingJobData("email", "admin@10086.com") //定义属性，增加属性 key 和 值，传递给任务
                .build();


        //添加 job 监听器
        MailJobListener mailJobListener = new MailJobListener();
        KeyMatcher<JobKey> keyMatcher = KeyMatcher.keyEquals(job.getKey());
        scheduler.getListenerManager().addJobListener(mailJobListener, keyMatcher);

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        Thread.sleep(2000);
        System.out.println("过5秒，调度停止 job");

        //key 就相当于这个Job的主键
        scheduler.interrupt(job.getKey());

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
      //  Thread.sleep(10000);
      //  scheduler.shutdown(true);
    }
}
