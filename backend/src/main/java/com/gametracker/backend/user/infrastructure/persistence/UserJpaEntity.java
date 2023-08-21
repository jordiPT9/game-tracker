package com.gametracker.backend.user.infrastructure.persistence;

import com.gametracker.backend.library_game.infrastructure.persistence.LibraryGameJpaEntity;
import com.gametracker.backend.role.infrastructure.RoleJpaEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserJpaEntity {
    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<LibraryGameJpaEntity> libraryGameJpaEntities;

    public UserJpaEntity() {
    }

    public UserJpaEntity(String id, String username, String password, String email, RoleJpaEntity role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String uuid) {
        this.id = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleJpaEntity getRole() {
        return role;
    }

    public void setRole(RoleJpaEntity role) {
        this.role = role;
    }
}