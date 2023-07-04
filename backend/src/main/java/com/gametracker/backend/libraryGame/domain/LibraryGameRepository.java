package com.gametracker.backend.libraryGame.domain;

import java.util.List;

public interface LibraryGameRepository {
    LibraryGame findById(String id);

    void save(LibraryGame libraryGame);

    void deleteAll();

    LibraryGame findByTitleAndUsername(String title, String username);

    void deleteById(String id);

    List<LibraryGame> findByUsername(String username);
}
