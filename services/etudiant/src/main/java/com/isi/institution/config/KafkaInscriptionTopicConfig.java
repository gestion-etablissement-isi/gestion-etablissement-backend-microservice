package com.isi.institution.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaInscriptionTopicConfig {
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
                .name("inscription-topic")
                .build();
    }
}
