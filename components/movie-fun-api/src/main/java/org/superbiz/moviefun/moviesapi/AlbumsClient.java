package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String albumsUrl;
    private RestOperations restOperations;

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public void addAlbum(AlbumInfo album) {

        logger.debug("Creating album with artist {}, and title {}, and url {}", album.getArtist(), album.getTitle(), albumsUrl);
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(albumsUrl);

        return restOperations.exchange(builder.toUriString(), GET, null, albumListType).getBody();
    }

    public AlbumInfo find(Long id) {
        return restOperations.getForEntity(albumsUrl + "/" + id,AlbumInfo.class).getBody();
    }

}
