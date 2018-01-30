package com.engagetech.engage.business;

import com.engagetech.engage.business.job.UpdateExchangeRateJob;
import com.engagetech.engage.config.EngageConfiguration;
import com.engagetech.engage.pico.ComponentManager;
import java.util.TimeZone;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JobManager {
    
    private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
    
    public void startUpdateExchangeRateJob() {
        EngageConfiguration config = ComponentManager.instance().getComponent(EngageConfiguration.class);
        
        try {
            JobDetail job = JobBuilder.newJob(UpdateExchangeRateJob.class)
                                      .withIdentity("UpdateExchangeRateJob", "Exchange Rate")
                                      .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("UpdateExchangeRate", "Exchange Rate")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(config.getCronJob().getUpdateExchangeRateJobCronExpression()).inTimeZone(TimeZone.getTimeZone("GMT")))
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling Exchange Rate Job", ex);
        }
    }
    
}
