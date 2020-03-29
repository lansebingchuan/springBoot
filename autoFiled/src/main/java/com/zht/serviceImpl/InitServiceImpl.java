package com.zht.serviceImpl;

import com.zht.schedule.ExecuteJob;
import com.zht.service.InitService;
import com.zht.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author zhanghaitao
 * @date 2020/3/26 0026
 */
@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private String XMLPATH = "xml/*.xml";

    //获取日志对象
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initXmlData() throws Exception {
        //得到xml文件数组
        Resource[] resources = resolver.getResources(XMLPATH);
        String fileMd5 = FileUtil.getFileMd5(resources);

        SAXReader reader = new SAXReader();
        for(Resource resource: resources){
            Document document = reader.read(resource.getInputStream());
            //文件根
            Element root = document.getRootElement();
            List<Element> groups = root.elements();
            for(Element group: groups){
                //组名
                String groupName = group.attributeValue("groupName", "default");
                //组值
                String groupValue = group.attributeValue("groupValue", "china");
                //组描述
                String groupDescription = group.attributeValue("description", "默认分组");
                List<Element> jobs = group.elements();
                for (Element job: jobs) {
                    //job名称
                    String jobName = job.attributeValue("name");
                    //job描述
                    String jobDescription = job.attributeValue("description");
                    //job执行方法
                    String jobInvokeTarget = job.attributeValue("invokeTarget");
                    //执行表达式
                    String jobCronExp = job.attributeValue("cronExp");
                    //创建一个任务
                    createScheduleJob(jobName, jobDescription, groupName, groupDescription, jobInvokeTarget, jobCronExp);
                }
            }
        }


    }

    //删除所有的定时任务
    private void deleteAllScheduleJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //获取所有的组名
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        if (!jobGroupNames.isEmpty()) {
            for (String jobGroupName : jobGroupNames) {
                //获取组的所有的任务key
                GroupMatcher<JobKey> tGroupMatcher = GroupMatcher.groupEquals(jobGroupName);
                //key集合
                Set<JobKey> jobKeys = scheduler.getJobKeys(tGroupMatcher);
                for (JobKey jobKey : jobKeys) {
                    //移除job任务
                    scheduler.deleteJob(jobKey);
                }
            }
        }
    }

    @Override
    public void runQuartzJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.triggerJob(jobKey);
    }

    @Override
    public void pauseQuartJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeQuartJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.resumeJob(jobKey);
    }

    /**
     * 创建Quartz任务
     *
     * @param jobName            任务名称
     * @param jobDescription     任务描述
     * @param groupName          分组名称
     * @param groupDescription   分组描述
     * @param jobInvokeTarget    调用目标字符串
     * @param jobCronExp         cron表达书
     * @throws SchedulerException 创建失败将抛出异常
     */
    private void createScheduleJob(String jobName, String jobDescription, String groupName, String groupDescription, String jobInvokeTarget, String jobCronExp) throws SchedulerException {
        //创建Quartz任务
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(ExecuteJob.class).withIdentity(JobKey.jobKey(jobName, groupName)).withDescription(jobDescription).build();
        //表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobCronExp);
        //构建CronTrigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(TriggerKey.triggerKey(jobName, groupName))
                .withSchedule(cronScheduleBuilder).build();
        //放入参数
        jobDetail.getJobDataMap().put("jobInvokeTarget", jobInvokeTarget);
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
