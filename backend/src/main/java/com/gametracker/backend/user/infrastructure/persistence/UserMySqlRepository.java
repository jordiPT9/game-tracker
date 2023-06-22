package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserMySqlRepository implements UserRepository {
    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;

    public UserMySqlRepository(UserMapper userMapper,
                               UserJpaRepository userJpaRepository) {
        this.userMapper = userMapper;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User findByUsername(String username) {
        UserJpaEntity userJpaEntity = userJpaRepository.findByUsername(username).orElse(null);

        if (userJpaEntity == null) {
            return null;
        }

        return userMapper.mapToDomainEntity(userJpaEntity);
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

