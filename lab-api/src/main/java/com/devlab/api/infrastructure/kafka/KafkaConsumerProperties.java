package com.devlab.api.infrastructure.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.kafka.consumer")
public record KafkaConsumerProperties(
    String groupId
) {

}

