package com.isi.institution.kafka;

import com.isi.institution.email.EmailService;
import com.isi.institution.kafka.etudiant.InscriptionConfirmation;
import com.isi.institution.notification.Notification;
import com.isi.institution.notification.NotificationRepository;
import com.isi.institution.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "inscription-topic")
    public void consumeInscriptionConfirmationNotifications(
            @Payload InscriptionConfirmation inscriptionConfirmation,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) throws MessagingException {
        log.info(format("Reçu inscription pour l'étudiant : %s", inscriptionConfirmation.etudiantId()));

        // Enregistrer la notification
        Notification notification = Notification.builder()
                .type(NotificationType.INSCRIPTION_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .inscriptionConfirmation(inscriptionConfirmation)
                .build();
        repository.save(notification);

        // Envoyer l'email de confirmation
        emailService.sendInscriptionConfirmationEmail(
                inscriptionConfirmation.email(),
                inscriptionConfirmation.nom(),
                inscriptionConfirmation.prenom(),
                inscriptionConfirmation.classeId(),
                inscriptionConfirmation.anneeScolaire()
        );
    }
}