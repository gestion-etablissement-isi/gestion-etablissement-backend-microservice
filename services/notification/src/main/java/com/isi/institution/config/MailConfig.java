package com.isi.institution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@EnableAsync
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Remplacez par votre serveur SMTP
        mailSender.setPort(587); // Port SMTP pour Gmail

        mailSender.setUsername("aliou.18.ndour@gmail.com"); // Remplacez par votre adresse email
        mailSender.setPassword("lzgt jtok qwld jfsx"); // Remplacez par votre mot de passe

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Active le mode debug pour le débogage

        return mailSender;
    }
}