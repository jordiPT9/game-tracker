package com.gametracker.backend.library_game.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryGameJpaRepository extends JpaRepository<LibraryGameJpaEntity, String> {
  LibraryGameJpaEntity findByTitleAndUser_Username(String title, String username);

  List<LibraryGameJpaEntity> findByUser_Username(String username);
}
