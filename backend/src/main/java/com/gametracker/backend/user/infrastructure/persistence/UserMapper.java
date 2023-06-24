package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.role.infrastructure.RoleJpaRepository;
import com.gametracker.backend.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final RoleJpaRepository roleJpaRepository;

    public UserMapper(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    public UserJpaEntity mapToJpaEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                roleJpaRepository.findByName(user.getRole())
        );
    }

    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getPassword(),
                userJpaEntity.getEmail(),
                userJpaEntity.getRole().getName()
        );
    }
}
