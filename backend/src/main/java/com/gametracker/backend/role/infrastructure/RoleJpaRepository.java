package com.gametracker.backend.role.infrastructure;

import com.gametracker.backend.role.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Integer> {
    RoleJpaEntity findByName(RoleName name);
}
