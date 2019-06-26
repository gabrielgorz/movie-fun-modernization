package org.superbiz.moviefun.moviesapi;

import java.util.Objects;

public class AlbumInfo {

    private Long id;

    private String artist;
    private String title;
    private int year;
    private int rating;

    public AlbumInfo() {
    }

    public AlbumInfo(String artist, String title, int year, int rating) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumInfo albumInfo = (AlbumInfo) o;
        return getYear() == albumInfo.getYear() &&
                getRating() == albumInfo.getRating() &&
                Objects.equals(getId(), albumInfo.getId()) &&
                Objects.equals(getArtist(), albumInfo.getArtist()) &&
                Objects.equals(getTitle(), albumInfo.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArtist(), getTitle(), getYear(), getRating());
    }
}
