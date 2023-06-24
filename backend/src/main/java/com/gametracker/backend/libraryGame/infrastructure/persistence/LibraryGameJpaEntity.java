package com.gametracker.backend.libraryGame.infrastructure.persistence;

import com.gametracker.backend.libraryGame.domain.LibraryGameStatus;
import com.gametracker.backend.user.infrastructure.persistence.UserJpaEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "library_games")
public class LibraryGameJpaEntity {
    @Id
    private String id;

    @Column(unique = true)
    private String title;

    private double rating;

    private LibraryGameStatus status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public LibraryGameJpaEntity() {
    }

    public LibraryGameJpaEntity(String id, String title, double rating, LibraryGameStatus status, UserJpaEntity user) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.status = status;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LibraryGameStatus getStatus() {
        return status;
    }

    public void setStatus(LibraryGameStatus status) {
        this.status = status;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity userJpaEntity) {
        this.user = userJpaEntity;
    }

    @Override
    public String toString() {
        return "LibraryGameJpaEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", status=" + status +
                ", userJpaEntity=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryGameJpaEntity that = (LibraryGameJpaEntity) o;
        return Double.compare(that.rating, rating) == 0 && Objects.equals(id, that.id) && Objects.equals(title, that.title) && status == that.status && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating, status, user);
    }

}