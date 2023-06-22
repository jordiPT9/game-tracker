package com.gametracker.backend.user.domain;

public interface UserRepository {
    User findByUsername(String username);

    void save(User user);

    void deleteById(String id);

    void delete(User user);

    void deleteAll();
}
