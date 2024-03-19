package com.gametracker.backend.user.domain;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findByUsername(String username);

  void save(User user);

  void deleteById(String id);

  void delete(User user);

  void deleteAll();
}
