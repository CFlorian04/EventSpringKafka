package com.cfa.jobs.letterJob;

import com.cfa.objects.lettre.Lettre;
import com.cfa.objects.lettre.LettreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableBinding({Source.class})
public class LetterTaskletSource implements Tasklet {

    @Autowired
    private LettreRepository lettreRepository;

    @Autowired
    @Output(Source.OUTPUT)
    private MessageChannel output;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // Lecture du fichier qui contient les messages
        Path path = Paths.get("Projet/EventCFA/letters.txt");
        List<String> lines = Files.readAllLines(path);

        // Pour chaque ligne
        for (String line : lines) {

            // Insertion dans la BDD
            Lettre lettre = this.saveLettre(line);

            // Envoi de la lettre par message
            this.sendLettre(lettre);


            // Trace de l'envoi du message dans un fichier output
            Files.write(
                    Paths.get("Projet/EventCFA/output.txt"),
                    Collections.singletonList("[\"" +  lettre.getCreationDate() + "\"] La demande \"" + line + "\" est traitée"),
                    StandardOpenOption.APPEND
            );
        }

        return RepeatStatus.FINISHED;
    }

    // Fonction de sauvegarde de la lettre
    private Lettre saveLettre(String contenu) {
        Lettre lettre = new Lettre();
        lettre.setMessage(contenu);
        lettre.setCreationDate(new Date());
        lettre = lettreRepository.save(lettre);
        log.info("Saving item to database: {}", lettre); // Le log ne s'affiche pas dans la console :(
        return lettre;
    }

    // Fonction d'envoi de la lettre dans un message
    private void sendLettre(Lettre lettre) {
        final Message<Lettre> message = MessageBuilder.withPayload(lettre).build();
        output.send(message);
        log.info("Sending item to Kafka: {}", lettre); // Le log ne s'affiche pas dans la console :(
    }
}
