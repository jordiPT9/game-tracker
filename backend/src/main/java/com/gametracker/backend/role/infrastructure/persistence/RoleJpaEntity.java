package com.gametracker.backend.role.infrastructure.persistence;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.infrastructure.persistence.UserJpaEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  private RoleName name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.DETACH)
  private Set<UserJpaEntity> userJpaEntities;

  public RoleJpaEntity() {}

  public RoleJpaEntity(RoleName name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public RoleName getName() {
    return name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }

  public Set<UserJpaEntity> getUserJpaEntities() {
    return userJpaEntities;
  }

  public void setUserJpaEntities(Set<UserJpaEntity> userJpaEntities) {
    this.userJpaEntities = userJpaEntities;
  }
}
