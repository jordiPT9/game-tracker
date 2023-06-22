package com.gametracker.backend.user.domain;

public interface RoleRepository {
    void save(RoleName roleName);

    void delete(RoleName roleName);

    void deleteAll();
}
