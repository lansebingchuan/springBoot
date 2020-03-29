package com.zht.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时器执行任务类
 *
 * @author zhanghaitao
 * @date 2020/3/26 0026
 */
public class ExecuteJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //类的执行方法 如： userService.listUser
        String classMethod = (String) jobExecutionContext.getMergedJobDataMap().get("jobExecutionContext");
    }
}
