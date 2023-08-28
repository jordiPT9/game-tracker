package com.gametracker.backend.shared.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseTruncator {

    private final EntityManager entityManager;

    @Autowired
    public DatabaseTruncator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void truncateAllTables() {
        disableForeignKeyChecks();
        executeTruncateQueries();
        enableForeignKeyChecks();
    }

    private void disableForeignKeyChecks() {
        Query query = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;");
        query.executeUpdate();
    }

    private void executeTruncateQueries() {
        List<String> queryStrings = getTruncateQueries();
        queryStrings.forEach(queryString -> {
            Query query = entityManager.createNativeQuery(queryString);
            query.executeUpdate();
        });
    }

    private List<String> getTruncateQueries() {
        String query = """
                SELECT CONCAT('TRUNCATE TABLE ',table_schema,'.',TABLE_NAME, ';') 
                FROM INFORMATION_SCHEMA.TABLES 
                WHERE table_schema IN ('game_tracker_bdd')
                """;
        List<?> resultList = entityManager.createNativeQuery(query).getResultList();

        return resultList
                .stream()
                .map(String.class::cast)
                .toList();
    }

    private void enableForeignKeyChecks() {
        Query query = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;");
        query.executeUpdate();
    }
}
