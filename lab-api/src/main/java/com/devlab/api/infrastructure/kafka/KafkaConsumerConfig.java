package com.devlab.api.infrastructure.kafka;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumerConfig {

  private final KafkaProperties properties;

  private ConsumerFactory<String, Object> createConsumerFactory(ObjectMapper objectMapper) {
    Map<String, Object> props = new HashMap<>();
    props.put(BOOTSTRAP_SERVERS_CONFIG, properties.bootstrapServers());
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.consumer().groupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

    return new DefaultKafkaConsumerFactory<>(
        props,
        new StringDeserializer(),
        new JsonDeserializer<>(objectMapper)
    );
  }

  private KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> createContainerFactory(
      ObjectMapper objectMapper
  ) {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(createConsumerFactory(objectMapper));
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }

  @Bean
  @Primary
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> camelCaseKafkaListenerContainerFactory(
      @Qualifier("mapperToCamelCase") ObjectMapper objectMapper
  ) {
    return createContainerFactory(objectMapper);
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> snakeCaseKafkaListenerContainerFactory(
      @Qualifier("mapperToSnakeCase") ObjectMapper objectMapper
  ) {
    return createContainerFactory(objectMapper);
  }

}