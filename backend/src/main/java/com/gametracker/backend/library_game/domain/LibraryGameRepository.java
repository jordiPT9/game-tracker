package com.gametracker.backend.library_game.domain;

import java.util.List;

public interface LibraryGameRepository {
    LibraryGame findById(String id);

    void save(LibraryGame libraryGame);

    LibraryGame findByTitleAndUsername(String title, String username);

    void deleteById(String id);

    List<LibraryGame> findByUsername(String username);

    Double getAverageGameRating(String gameTitle);
}
