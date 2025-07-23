package com.devlab.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper mapperToSnakeCase() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    return mapper;
  }

  @Primary
  @Bean
  public ObjectMapper mapperToCamelCase() {
    // todo 셋팅
    return new ObjectMapper();
  }
}