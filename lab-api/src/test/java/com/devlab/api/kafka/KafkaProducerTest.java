package com.devlab.api.kafka;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devlab.api.LabApiApplicationTests;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

public class KafkaProducerTest extends LabApiApplicationTests {


  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Test
  @DisplayName("메세지를 발송한다.")
  public void test() throws ExecutionException, InterruptedException, TimeoutException {
    // given
    String topic = "test";
    String message = "test";

    // when
    CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);

    // then
    SendResult<String, Object> result = future.get(5, TimeUnit.SECONDS);
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(topic, result.getRecordMetadata().topic()),
        () -> assertTrue(result.getRecordMetadata().hasOffset()),
        () -> assertEquals(message, result.getProducerRecord().value())
    );
  }
}
