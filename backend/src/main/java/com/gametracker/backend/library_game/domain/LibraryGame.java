package com.gametracker.backend.library_game.domain;

import java.util.Objects;

public class LibraryGame {
  private String id;
  private String title;
  private double rating;
  private LibraryGameStatus status;
  private String username;

  private LibraryGame() {}

  public static LibraryGameBuilder builder() {
    return new LibraryGameBuilder();
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

  private void setId(String id) {
    this.id = id;
  }

  private void setTitle(String title) {
    this.title = title;
  }

  private void setRating(double rating) {
    this.rating = rating;
  }

  private void setStatus(LibraryGameStatus status) {
    this.status = status;
  }

  private void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "LibraryGame{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", rating="
        + rating
        + ", status="
        + status
        + ", username='"
        + username
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LibraryGame that = (LibraryGame) o;
    return Double.compare(that.rating, rating) == 0
        && Objects.equals(id, that.id)
        && Objects.equals(title, that.title)
        && status == that.status
        && Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, rating, status, username);
  }

  public static class LibraryGameBuilder {
    private final LibraryGame libraryGame = new LibraryGame();

    private LibraryGameBuilder() {}

    public LibraryGameBuilder id(String id) {
      libraryGame.setId(id);
      return this;
    }

    public LibraryGameBuilder title(String title) {
      libraryGame.setTitle(title);
      return this;
    }

    public LibraryGameBuilder rating(double rating) {
      libraryGame.setRating(rating);
      return this;
    }

    public LibraryGameBuilder status(LibraryGameStatus status) {
      libraryGame.setStatus(status);
      return this;
    }

    public LibraryGameBuilder username(String username) {
      libraryGame.setUsername(username);
      return this;
    }

    public LibraryGame build() {
      return libraryGame;
    }
  }
}
