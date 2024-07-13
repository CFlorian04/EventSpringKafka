package com.cfa.customjobservice;

import com.cfa.jobs.simplejob.LetterSink;
import com.cfa.objects.lettre.Lettre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;

/**
 * Processor.input : to listen msg from job-master-server
 * Processor.output : to send msg to job-master-server
 */


@Slf4j
@Component
@EnableBinding(LetterSink.class)
public class JobProcessor {

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  // Ecoute de l'envoi de message
  @StreamListener(LetterSink.INPUT)
  public void listenLetter(final Lettre parMsg) {
    log.info("[Worker START] Received message: {}", parMsg);

    // Modification de l'header du message
    log.info("[Worker TRANSFORM] Transforming message: {}", parMsg);
    final Message<Lettre> locMessageEnd =
            MessageBuilder.withPayload(parMsg)
                    .setHeader("custom_info", "end")
                    .build();

    log.info("[Worker END] Sending transformed message: {}", locMessageEnd);

    // _processor.output().send(locMessageEnd);
  }

  // Test pour le Remote Partionning

//  @Bean
//  public Step workerStep() {
//    return stepBuilderFactory.get("workerStep")
//            .<String, String>chunk(10)
//            .reader(itemReader)
//            .processor(itemProcessor)
//            .writer(itemWriter).build();
//  }
//
//  private ItemReader<String> itemReader = new ItemReader<String>() {
//    return new ItemReader<String>;
//  }
//
//  private ItemProcessor itemProcessor = new ItemProcessor() {
//
//  }
//
//  private ItemWriter<String> itemWriter = new ItemWriter<String>() {
//
//  }


}
