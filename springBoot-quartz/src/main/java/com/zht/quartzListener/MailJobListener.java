package com.zht.quartzListener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MailJobListener implements JobListener {//定时器监听器类
 
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "listener of mail job";
    }
 
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // TODO Auto-generated method stub
        System.out.println("取消执行：\t "+context.getJobDetail().getKey());
    }
 
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {//常用
        // TODO Auto-generated method stub
        System.out.println("准备执行：\t "+context.getJobDetail().getKey());
    }
 
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException arg1) {//常用
        // TODO Auto-generated method stub
        System.out.println("执行结束：\t "+context.getJobDetail().getKey());
        System.out.println();
    }
}