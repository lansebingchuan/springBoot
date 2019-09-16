package com.zht.quartzTimeTest;

import com.zht.quartzTimeJob.EmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zht
 * @create 2019-09-05 9:19
 * http://cron.qqe2.com/   cron定时日期生成器 -网址
 */
public class EmailTrigger1 {

    public static void main(String[] args) throws Exception {
        try {
            scheduler();
        } catch (ObjectAlreadyExistsException e) {
            System.err.println("发现任务已经在数据库存在了，直接从数据库里运行:"+ e.getMessage());
            // TODO Auto-generated catch block
            EmailTrigger.resumeJobFromDatabase();
        }
    }

    public static   void scheduler() throws SchedulerException, InterruptedException {
        //设置排除日历
        AnnualCalendar holidays = new AnnualCalendar();

        GregorianCalendar nationalDay = new GregorianCalendar(2019, 9, 10);  // 排除今天的时间2017年11月27日（月份是从0～11的）

        holidays.setDayExcluded(nationalDay,true); //排除的日期，如果为false则为包含*/

        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //向Scheduler注册排除日历
        scheduler.addCalendar("holidays",holidays,false,false);

        Date end = new Date();
        end.setTime(new Date().getTime()+10000);
        //定义一个触发器
        Trigger trigger = newTrigger().withIdentity("trigger2", "group1") //定义名称（唯一）和所属的组
                .startNow()
                //.startAt(new Date())  //设置具体的开始时间
                //.endAt(end)   //设置具体的结束时间
                .withSchedule(CronScheduleBuilder  //适用于某个固定的时间开始
                        .cronSchedule("30 37/1 * * * ? *") //37分钟的时候，30秒时执行定时，每隔一分钟再次执行
                        //"?" 该字符只在日期和星期字段中使用
                        //分别是秒/分/时/日/月/周/年
                )
                .modifiedByCalendar("holidays")   //将我们设置好的（排除日历）Calander与trigger绑定
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
/*
    表示式	说明  ： 秒/分/时/日/月/周/年
        0 0 12 * * ? 	每天12点运行
        0 15 10 ? * *	每天10:15运行
        0 15 10 * * ?	每天10:15运行
        0 15 10 * * ? *	每天10:15运行
        0 15 10 * * ? 2008	在2008年的每天10：15运行
        0 * 14 * * ?	每天14点到15点之间每分钟运行一次，开始于14:00，结束于14:59。
        0 0/5 14 * * ?	每天14点到15点每5分钟运行一次，开始于14:00，结束于14:55。
        0 0/5 14,18 * * ?	每天14点到15点每5分钟运行一次，此外每天18点到19点每5钟也运行一次。
        0 0-5 14 * * ?	每天14:00点到14:05，每分钟运行一次。
        0 10,44 14 ? 3 WED	3月每周三的14:10分到14:44，每分钟运行一次。
        0 15 10 ? * MON-FRI	每周一，二，三，四，五的10:15分运行。
        0 15 10 15 * ?	每月15日10:15分运行。
        0 15 10 L * ?	每月最后一天10:15分运行。
        0 15 10 ? * 6L	每月最后一个星期五10:15分运行。
        0 15 10 ? * 6L 2007-2009	在2007,2008,2009年每个月的最后一个星期五的10:15分运行。
        0 15 10 ? * 6#3	每月第三个星期五的10:15分运行。
        //1.每日10点15分触发      0 15 10 ？* *
        //2.每天下午的2点到2点59分（正点开始，隔5分触发）       0 0/5 14 * * ?
        //3.从周一到周五每天的上午10点15触发      0 15 10 ? MON-FRI
        //4.每月的第三周的星期五上午10点15触发     0 15 10 ? * 6#3
        //5.2016到2017年每月最后一周的星期五的10点15分触发   0 15 10 ? * 6L 2016-2017*/
