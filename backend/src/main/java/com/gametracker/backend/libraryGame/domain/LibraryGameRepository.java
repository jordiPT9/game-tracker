package com.gametracker.backend.libraryGame.domain;

public interface LibraryGameRepository {
    LibraryGame findById(String id);

    void save(LibraryGame libraryGame);

    void deleteAll();

    LibraryGame findByTitleAndUsername(String title, String username);

    void deleteById(String id);
}
