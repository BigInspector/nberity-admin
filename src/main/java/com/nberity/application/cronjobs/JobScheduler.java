package com.nberity.application.cronjobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job elkoProductsJob;

  //  @Scheduled(cron = "0 0 */3 * * *")
    //  @Scheduled(cron = "*/5 * * * * MON-FRI")
    public void runElkoProductsJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParameters();
        jobLauncher.run(elkoProductsJob, parameters);
    }

}
