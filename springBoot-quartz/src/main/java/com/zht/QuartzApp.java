package com.zht;

import com.zht.quartzTime.EmailTrigger;
import com.zht.quartzTime.EmailTrigger1;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

/**
 * @author zht
 * @create 2019-09-05 9:10
 * 1、设置session时间，普通的不可用，需要在sessionListener(监听器)创建session的时候设置session时间
 * 2、配置拦截器-- IntercepterApp
 */
@ServletComponentScan //Tomcat servlet扫描
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//无数据库运行
public class QuartzApp {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApp.class, args);
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {//配置单例时钟
        return  StdSchedulerFactory.getDefaultScheduler();
    }

   // @Bean(initMethod = "scheduler")
    public EmailTrigger emailTrigger() throws SchedulerException {//创建一个计时器任务
        return new EmailTrigger();
    }

   // @Bean(initMethod = "scheduler")
    public EmailTrigger1 emailTrigger1() throws SchedulerException {//创建一个计时器任务
        return new EmailTrigger1();
    }

}
