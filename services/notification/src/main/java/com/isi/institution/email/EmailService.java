package com.isi.institution.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.*;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class EmailService {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendInscriptionConfirmationEmail(
            String destinationEmail,
            String nom,
            String prenom,
            String classeId,
            String anneeScolaire
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("aliou.18.ndour@gmail.com");

        final String templateName = EmailTemplates.INSCRIPTION_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("etudiantName", nom + " " + prenom);
        variables.put("classeId", classeId);
        variables.put("anneeScolaire", anneeScolaire);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(EmailTemplates.INSCRIPTION_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("Email d'inscription envoyé à {} ", destinationEmail);
        } catch (MessagingException e) {
            log.error("Impossible d'envoyer l'email à {} : {}", destinationEmail, e.getMessage());
            throw e;
        }
    }

}
