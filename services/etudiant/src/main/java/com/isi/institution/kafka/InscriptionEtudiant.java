package com.isi.institution.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class InscriptionEtudiant {

    private final KafkaTemplate<String, InscriptionConfirmation> kafkaTemplate;

    public void sendInscriptionConfirmation(InscriptionConfirmation inscriptionConfirmation) {
        log.info("Sending inscription confirmation for student: {}", inscriptionConfirmation.etudiantId());

        Message<InscriptionConfirmation> message = MessageBuilder
                .withPayload(inscriptionConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "inscription-topic")
                .build();

        CompletableFuture<SendResult<String, InscriptionConfirmation>> future = kafkaTemplate.send(message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Inscription confirmation sent successfully. Offset: {}",
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send inscription confirmation", ex);
            }
        });
    }
}