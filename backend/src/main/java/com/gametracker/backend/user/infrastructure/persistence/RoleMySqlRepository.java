package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.user.domain.RoleName;
import com.gametracker.backend.user.domain.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleMySqlRepository implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    public RoleMySqlRepository(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public void save(RoleName roleName) {
        RoleJpaEntity roleJpaEntity = new RoleJpaEntity(roleName);
        roleJpaRepository.save(roleJpaEntity);
    }

    @Override
    public void delete(RoleName roleName) {
        RoleJpaEntity roleJpaEntity = new RoleJpaEntity(roleName);
        roleJpaRepository.delete(roleJpaEntity);
    }

    @Override
    public void deleteAll() {
        roleJpaRepository.deleteAll();
    }
}

