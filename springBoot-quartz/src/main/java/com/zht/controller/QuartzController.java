package com.zht.controller;

import com.zht.listener.SessionListener;
import com.zht.quartzTimeJob.EmailJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zht
 * @create 2019-09-05 10:16
 */

@Controller
public class QuartzController {

    @Autowired
    private Scheduler scheduler;  //创建调度器

    @RequestMapping("/quartz")
    @ResponseBody
    public String quartz(@RequestParam(value = "name" , required = false) String name)
    {
        return "您好："+name+"当前在线人数："+SessionListener.online;
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/success")
    public String success(){
        return "success";
    }

    @RequestMapping("/")
    @ResponseBody
    public String startStudent() {
        try {
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
            return "定时开启";
        }catch (SchedulerException s){
            s.printStackTrace();
        }
        return "定时开启失败";
    }
}
