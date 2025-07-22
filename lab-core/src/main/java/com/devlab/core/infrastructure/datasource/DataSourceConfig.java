package com.devlab.core.infrastructure.datasource;

import com.devlab.core.infrastructure.datasource.ReplicationRoutingDataSource.RoutingDataType;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Slf4j
@Configuration
@EnableJpaRepositories(
    basePackages = "com.devlab.core",
    entityManagerFactoryRef = "devLabEntityManagerFactory",
    transactionManagerRef = "devLabTransactionManager"
)
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.slave")
  public DataSource slaveDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean devLabEntityManagerFactory(
      @Qualifier("defaultDataSource") DataSource defaultDataSource,
      EntityManagerFactoryBuilder builder
  ) {

    return builder
        .dataSource(defaultDataSource)
        .packages("com.devlab.core")
        .persistenceUnit("default")
        .build();
  }

  @Bean
  @Primary
  public PlatformTransactionManager devLabTransactionManager(
      @Qualifier("devLabEntityManagerFactory") EntityManagerFactory factory
  ) {
    return new JpaTransactionManager(factory);
  }


  // todo: master, slave 추후 분리
  @Bean
  public DataSource routingDataSource(
      @Qualifier("masterDataSource") DataSource masterDataSource,
      @Qualifier("slaveDataSource") DataSource slaveDataSource
  ) {
    ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

    Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
    dataSourceMap.put(RoutingDataType.MASTER, masterDataSource);
    dataSourceMap.put(RoutingDataType.SLAVE, slaveDataSource);
    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @Primary
  @Bean
  public DataSource defaultDataSource(
      @Qualifier("routingDataSource") DataSource routingDataSource
  ) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

}