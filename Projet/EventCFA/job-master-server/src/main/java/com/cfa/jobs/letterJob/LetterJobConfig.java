package com.cfa.jobs.letterJob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LetterJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RemoteChunkingManagerStepBuilderFactory managerStepBuilderFactory;


    // Création du job : Appel du step
    @Bean
    public Job letterJob(Step letterStep) {
        return jobBuilderFactory.get("letterJob")
                .incrementer(new RunIdIncrementer())
                .flow(letterStep)
                .end()
                .build();
    }

    // Création du step : Appel du tasklet
    @Bean
    public Step letterStep() {
        return stepBuilderFactory.get("letterStep")
                .tasklet(new LetterTaskletSource())
                .build();
    }

    // Test pour le Remote Partionning

//    @Bean
//    public Step masterStep() {
//        return stepBuilderFactory.get("masterStep")
//                .partitioner("workerStep", partitioner())
//                .partitionHandler(partitionHandler())
//                .gridSize(2) // Nombre de serveurs de travail
//                .build();
//    }
//
//    private PartitionHandler partitionHandler() {
//    }
//
//    private Partitioner partitioner() {
//    }



}
