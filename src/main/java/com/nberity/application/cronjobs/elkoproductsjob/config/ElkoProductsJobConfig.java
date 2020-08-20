package com.nberity.application.cronjobs.elkoproductsjob.config;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElkoProductsJobConfig {

    @Bean
    public Job elkoProductsJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                               ItemReader<ElkoProduct> allElkoProductsItemReader, ItemWriter<ElkoProduct> allElkoProductsItemWriter,
                               ItemReader<ElkoProduct> elkoProductAttributeItemReader, ItemWriter<ElkoProduct> elkoProductAttributeItemWriter) {
        Step step = stepBuilderFactory.get("step1")
                .<ElkoProduct, ElkoProduct>chunk(1000)
                .reader(allElkoProductsItemReader)
                .writer(allElkoProductsItemWriter)
                .build();

        Step step2 = stepBuilderFactory.get("step2")
                .<ElkoProduct, ElkoProduct>chunk(1000)
                .reader(elkoProductAttributeItemReader)
                .writer(elkoProductAttributeItemWriter)
                .build();

        return jobBuilderFactory.get("elkoProductsJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .next(step2)
                .build();

    }
}
