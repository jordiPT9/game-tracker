package com.gametracker.backend.role.domain;

public interface RoleRepository {
    void save(RoleName roleName);

    void delete(RoleName roleName);

    void deleteAll();
}
