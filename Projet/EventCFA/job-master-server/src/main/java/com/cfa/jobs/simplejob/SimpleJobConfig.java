package com.cfa.jobs.simplejob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.transform.Source;
import java.util.Arrays;

@Configuration
public class SimpleJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public Source sources;

    ///////////////////////////////////////////////////////////////////////////
    // Exercices 2.1 : Créer un nouveau Job “SimpleJob” qui contient deux Steps
    @Bean
    public Job simpleJob() {
        return this.jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<String, String>chunk(1)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
                .<String, String>chunk(1)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> itemReader() {
        return new ListItemReader<>(Arrays.asList("one", "two", "three"));
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return item -> {
            System.out.println("Processing item: " + item);
            return item.toUpperCase();
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> items.forEach(System.out::println);
    }

    /////////////////////////////////////////////////////////////////////
    // Exercices 2.2 : Créer un nouveau Step “SimpleStep” avec un Tasklet

    @Bean
    public Step simpleStep() { // Nom de méthode en camelCase
        return this.stepBuilderFactory.get("simpleStep")
                .tasklet(new SimpleTasklet(sources))
                .build();
    }

}
