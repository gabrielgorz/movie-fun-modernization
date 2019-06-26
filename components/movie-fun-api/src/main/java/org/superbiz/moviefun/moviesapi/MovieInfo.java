package org.superbiz.moviefun.moviesapi;

import java.util.Objects;

public class MovieInfo {

    private long id;
    private String director;
    private String title;
    private int year;
    private String genre;
    private int rating;

    public MovieInfo() {
    }

    public MovieInfo(String title, String director, String genre, int rating, int year) {
        this.director = director;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public MovieInfo(String director, String title, int year) {
        this.director = director;
        this.title = title;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieInfo movieInfo = (MovieInfo) o;
        return getId() == movieInfo.getId() &&
                getYear() == movieInfo.getYear() &&
                getRating() == movieInfo.getRating() &&
                getDirector().equals(movieInfo.getDirector()) &&
                getTitle().equals(movieInfo.getTitle()) &&
                getGenre().equals(movieInfo.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDirector(), getTitle(), getYear(), getGenre(), getRating());
    }
}
