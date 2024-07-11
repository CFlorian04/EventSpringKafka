package com.cfa.jobs.letterJob;

import com.cfa.objects.lettre.Lettre;
import com.cfa.objects.lettre.LettreRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class LetterTaskletSource implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(LetterTaskletSource.class);

    @Autowired
    private LettreRepository lettreRepository;

    @Autowired
    private KafkaTemplate<String, Lettre> kafkaTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Path path = Paths.get("Projet/EventCFA/letters.txt");
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {

            Lettre lettre = this.saveLettre(line);
            this.sendLettre(lettre);

            Files.write(Paths.get("Projet/EventCFA/output.txt"),
                    Collections.singletonList("La demande \"" + line + "\" est traitée"),
                    StandardOpenOption.APPEND);
        }

        return RepeatStatus.FINISHED;
    }

    private Lettre saveLettre(String contenu) {
        Lettre lettre = new Lettre();
        lettre.setMessage(contenu);
        lettre.setCreationDate(new Date());
        lettre = lettreRepository.save(lettre);
        log.info("Saving item to database: {}", lettre);
        return lettre;
    }

    private void sendLettre(Lettre lettre) {
        kafkaTemplate.send("lettreTopic", lettre);
        log.info("Sending item to Kafka: {}", lettre);
    }
}
