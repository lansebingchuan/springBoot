package com.zht.service;

import org.quartz.SchedulerException;

/**
 * @author zhanghaitao
 * @date 2020/3/26 0026
 */
public interface InitService {

    /**
     * 开启自动任务
     */
    void initXmlData() throws Exception;

    /**
     * 立即执行任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务分组
     * @throws SchedulerException 执行失败将抛出异常
     */
    void runQuartzJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务分组
     * @throws SchedulerException 暂停失败将抛出异常
     */
    void pauseQuartJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务分组
     * @throws SchedulerException 恢复失败将抛出异常
     */
    void resumeQuartJob(String jobName, String jobGroup) throws SchedulerException;
}
