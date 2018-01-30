package com.engagetech.engage.business.job;

import com.engagetech.engage.business.ExpenseManager;
import com.engagetech.engage.pico.ComponentManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UpdateExchangeRateJob implements Job {
    
    Logger logger = (Logger) LoggerFactory.getLogger(UpdateExchangeRateJob.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        ExpenseManager expenseManager = ComponentManager.instance().getComponent(ExpenseManager.class);
        expenseManager.updateExchangeRate();
    }
    
}
