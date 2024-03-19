package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMySqlRepository implements UserRepository {
  private final UserMapper userMapper;
  private final UserJpaRepository userJpaRepository;

  public UserMySqlRepository(UserMapper userMapper, UserJpaRepository userJpaRepository) {
    this.userMapper = userMapper;
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    UserJpaEntity userJpaEntity = userJpaRepository.findByUsername(username);

    return userJpaEntity == null
        ? Optional.empty()
        : Optional.of(userMapper.mapToDomainEntity(userJpaEntity));
  }

  @Override
  public void save(User user) {
    UserJpaEntity userJpaEntity = userMapper.mapToJpaEntity(user);
    userJpaRepository.save(userJpaEntity);
  }

  @Override
  public void deleteById(String id) {
    userJpaRepository.deleteById(id);
  }

  @Override
  public void delete(User user) {
    UserJpaEntity userJpaEntity = userMapper.mapToJpaEntity(user);

    userJpaRepository.delete(userJpaEntity);
  }

  @Override
  public void deleteAll() {
    userJpaRepository.deleteAll();
  }
}
