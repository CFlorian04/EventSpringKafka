package com.cfa.jobs.letterJob;

import com.cfa.objects.lettre.Lettre;
import com.cfa.objects.lettre.LettreRepository;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Configuration
public class LetterJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LettreRepository lettreRepository;

    private List<Lettre> LettreList = new ArrayList<>();

    @Bean
    public Job letterJob() {

        log.info("letter JOB Executing .....");
        return this.jobBuilderFactory.get("letterJob")
                .start(letterStep())
                .build();
    }

    @Bean
    public Step letterStep() {
        return this.stepBuilderFactory.get("letterStep")
                .<String, String>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> itemReader() {
        List<String> lettersList = new ArrayList<>();
//        String lettersFile = "letters.txt";
//
//        try (BufferedReader bf = new BufferedReader(new FileReader(lettersFile))) {
//            String line;
//            while ((line = bf.readLine()) != null) {
//                lettersList.add(line);
//                log.info("[FILE] : {}", line);
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }

        lettersList.add("test 1");
        lettersList.add("test 2");
        lettersList.add("test 3");
        lettersList.add("test 4");

        for (String s : lettersList) {
            log.info(s);
        }

        return new ListItemReader<>(lettersList);
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return lettre -> {
            log.info("Processing Item: {}", lettre);
            Thread.sleep(500); // Simulation du traitement
            return lettre;
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return lettres -> {
            for (String message : lettres) {
                Lettre lettre = new Lettre();
                lettre.setMessage(message);
                lettre.setCreationDate(new Date());
                lettreRepository.save(lettre);
                lettreRepository.flush();
                log.info("Saving item to database: {}", lettre);


            }
        };
    }
}
