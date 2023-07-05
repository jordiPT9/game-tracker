package com.gametracker.backend.libraryGame.infrastructure.persistence;

import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MySqlLibraryGameRepository implements LibraryGameRepository {
    private final LibraryGameJpaRepository libraryGameJpaRepository;
    private final LibraryGameMapper libraryGameMapper;

    public MySqlLibraryGameRepository(LibraryGameJpaRepository libraryGameJpaRepository, LibraryGameMapper libraryGameMapper) {
        this.libraryGameJpaRepository = libraryGameJpaRepository;
        this.libraryGameMapper = libraryGameMapper;
    }

    @Override
    public void save(LibraryGame libraryGame) {
        LibraryGameJpaEntity libraryGameJpaEntity = libraryGameMapper.mapToJpaEntity(libraryGame);
        libraryGameJpaRepository.save(libraryGameJpaEntity);
    }

    @Override
    public LibraryGame findByTitleAndUsername(String title, String username) {
        LibraryGameJpaEntity libraryGameJpaEntity = libraryGameJpaRepository.findByTitleAndUser_Username(title, username);

        if (libraryGameJpaEntity == null) {
            return null;
        }

        return libraryGameMapper.mapToDomainEntity(libraryGameJpaEntity);
    }

    @Override
    public void deleteById(String id) {
        libraryGameJpaRepository.deleteById(id);
    }

    @Override
    public List<LibraryGame> findByUsername(String username) {
        List<LibraryGameJpaEntity> libraryGameJpaEntities = libraryGameJpaRepository.findByUser_Username(username);

        return libraryGameJpaEntities
                .stream()
                .map(libraryGameMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LibraryGame findById(String id) {
        LibraryGameJpaEntity libraryGameJpaEntity = libraryGameJpaRepository.findById(id).orElse(null);

        if (libraryGameJpaEntity == null) {
            return null;
        }

        return libraryGameMapper.mapToDomainEntity(libraryGameJpaEntity);
    }
}
