package com.nberity.application.cronjobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job elkoProductsJob;

    @Scheduled(cron = "0 0 9,18 * * *")
    public void runElkoProductsJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(elkoProductsJob, new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters());
    }

}
