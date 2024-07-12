package com.cfa.jobs.letterJob;

import com.cfa.objects.lettre.Lettre;
import com.cfa.objects.lettre.LettreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LetterJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job letterJob(Step letterStep) {
        return jobBuilderFactory.get("letterJob")
                .incrementer(new RunIdIncrementer())
                .flow(letterStep)
                .end()
                .build();
    }

    @Bean
    public Step letterStep(Tasklet letterTasklet) {
        return stepBuilderFactory.get("letterStep")
                .tasklet(letterTasklet)
                .build();
    }

    @Bean
    public Tasklet letterTasklet() {
        return new LetterTaskletSource();
    }
}
