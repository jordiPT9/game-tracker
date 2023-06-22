package com.gametracker.backend.libraryGame.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryGameJpaRepository extends JpaRepository<LibraryGameJpaEntity, String> {
    LibraryGameJpaEntity findByTitleAndUser_Username(String title, String username);
}
