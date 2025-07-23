package com.devlab.api.infrastructure.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("spring.kafka")
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public record KafkaProperties(
    String bootstrapServers,
    KafkaConsumerProperties consumer
) {

}

