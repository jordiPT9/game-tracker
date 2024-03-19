package com.gametracker.backend.util;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTruncator {

  private final EntityManager entityManager;

  @Autowired
  public DatabaseTruncator(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public void truncateAllTables() {
    // disable foreign checks
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

    // truncate all tables
    entityManager
        .createNativeQuery("SHOW TABLES")
        .getResultList()
        .forEach(
            tableName -> {
              String truncateQuery = "TRUNCATE TABLE " + tableName;
              entityManager.createNativeQuery(truncateQuery).executeUpdate();
            });

    // enable foreign checks again
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
  }
}
