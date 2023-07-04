package com.gametracker.backend.role.infrastructure;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.role.domain.RoleRepository;
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

    @Override
    public RoleName findByRoleName(RoleName roleName) {
        RoleJpaEntity roleJpaEntity = roleJpaRepository.findByName(roleName);

        return RoleName.valueOf(roleJpaEntity.getName().name());
    }
}

