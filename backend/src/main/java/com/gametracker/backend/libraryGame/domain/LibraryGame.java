package com.gametracker.backend.libraryGame.domain;

import java.util.Objects;

public class LibraryGame {
    private final String id;
    private final String title;
    private final double rating;
    private final LibraryGameStatus status;
    private final String username;

    public LibraryGame(String id, String title, double rating, LibraryGameStatus status, String username) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.status = status;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public LibraryGameStatus getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "LibraryGame{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryGame that = (LibraryGame) o;
        return Double.compare(that.rating, rating) == 0 && Objects.equals(id, that.id) && Objects.equals(title, that.title) && status == that.status && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating, status, username);
    }
}
