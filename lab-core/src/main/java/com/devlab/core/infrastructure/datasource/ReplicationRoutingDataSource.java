package com.devlab.core.infrastructure.datasource;

import static com.devlab.core.infrastructure.datasource.ReplicationRoutingDataSource.RoutingDataType.MASTER;
import static com.devlab.core.infrastructure.datasource.ReplicationRoutingDataSource.RoutingDataType.SLAVE;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
  enum RoutingDataType {
    SLAVE,
    MASTER
  }

  public Object determineCurrentLookupKey() {
    boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//    return isReadOnly ? SLAVE : MASTER;
    return MASTER;
  }
}

