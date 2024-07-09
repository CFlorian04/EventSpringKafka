package com.cfa.jobs.simplejob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.cloud.stream.annotation.EnableBinding;

import javax.xml.transform.Source;

@Slf4j
@RequiredArgsConstructor
@EnableBinding({Source.class})
public class SimpleTasklet implements Tasklet {

    private final Source source;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug("executing SimpleTasklet");
        return RepeatStatus.FINISHED;
    }

}
