package com.cfa.customjobservice;


import com.cfa.jobs.letterJob.LetterSink;
import com.cfa.objects.lettre.Lettre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Processor.input : to listen msg from job-master-server
 * Processor.output : to send msg to job-master-server
 */


@Slf4j
@Component
@EnableBinding(LetterSink.class)
public class JobProcessor {

  @StreamListener(LetterSink.INPUT)
  public void listenLetter(final Lettre parMsg) {
    log.info("[Worker START] Received message: {}", parMsg);

    final Lettre locPayload = parMsg;
    log.info("[Worker TRANSFORM] Transforming message: {}", locPayload);
    final Message<Lettre> locMessageEnd =
            MessageBuilder.withPayload(locPayload)
                    .setHeader("custom_info", "end")
                    .build();

    log.info("[Worker END] Sending transformed message: {}", locMessageEnd);

//    _processor.output().send(locMessageEnd);
  }
}
