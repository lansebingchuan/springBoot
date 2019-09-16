package com.zht.quartzTimeJob;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zht
 * @create 2019-09-05 9:13
 */
//并发的情况下面可以使用
@DisallowConcurrentExecution//默认情况下，无论这次任务是否完成，只要规定时间到了，都会执行一次任务
                            //执行任务接口  //中断任务接口
public class EmailJob  implements Job , InterruptableJob  {

    private  boolean stop = false; //那么只触发一次，没有加static ，就是哪一段定时刚刚执行到的时候，这个定时有效，是否确认中断
    private static   boolean stop1 = false; //每次定时都触发对每一次的定时都起作用的静态类量  -是否确认中断

    @Override
    public void execute(JobExecutionContext job) throws  JobExecutionException{//执行任务接口
        JobDetail jobDetail;
        try {
            jobDetail = job.getJobDetail();//获取任务数据集合
            String email = (String) jobDetail.getJobDataMap().get("email");//获取指定任务名称
            jobDetail.getJobDataMap().put("email", email+"1");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String now = simpleDateFormat.format(new Date());
            System.out.printf("给邮件地址 %s 发出了一封定时邮件, 当前时间是: %s%n" ,email, now);
            try {//模拟睡眠，查看 @DisallowConcurrentExecution 注解 ， 解决并发，任务执行完毕才能执行下一个
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (i < 5 ){
                if (stop) break;
                i++;
                System.out.println("现在：i="+i);
            }
            i = 0;
           // int a= 1/ i ;  //模拟触发异常
        }catch (Exception e){//出现这个异常取消定时器
            System.out.println("发生了异常，取消这个Job 对应的所有调度");//不然还会执行这个调度，直到结束
            JobExecutionException je =new JobExecutionException(e);
            je.setUnscheduleAllTriggers(true);
            throw je;
        }
        System.out.println("一个任务执行完毕"+jobDetail.getKey().getGroup()+"."+jobDetail.getKey().getName());
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {//中断时候调用接口
        System.out.println("调度叫停");
        stop1= true;//为true表示确认停止
    }
}
