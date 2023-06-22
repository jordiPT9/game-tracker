package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.user.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Integer> {
    RoleJpaEntity findByName(RoleName name);
}
