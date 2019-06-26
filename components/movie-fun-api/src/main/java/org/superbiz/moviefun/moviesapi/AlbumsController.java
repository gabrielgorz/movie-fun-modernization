package org.superbiz.moviefun.moviesapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

@Controller
@RequestMapping({"/albums"})
public class AlbumsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsClient albumsClient;
    private final BlobStore blobStore;

    public AlbumsController(AlbumsClient albumsClient, BlobStore blobStore) {
        this.albumsClient = albumsClient;
        this.blobStore = blobStore;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("albums", this.albumsClient.getAlbums());
        return "albums";
    }

    @GetMapping({"/{albumId}"})
    public String details(@PathVariable long albumId, Map<String, Object> model) {
        model.put("album", this.albumsClient.find(albumId));
        return "albumDetails";
    }

    @PostMapping({"/{albumId}/cover"})
    public String uploadCover(@PathVariable Long albumId, @RequestParam("file") MultipartFile uploadedFile) {
        this.logger.debug("Uploading cover for album with id {}", albumId);
        if (uploadedFile.getSize() > 0L) {
            try {
                this.tryToUploadCover(albumId, uploadedFile);
            } catch (IOException var4) {
                this.logger.warn("Error while uploading album cover", var4);
            }
        }

        return String.format("redirect:/albums/%d", albumId);
    }

    @GetMapping({"/{albumId}/cover"})
    public HttpEntity<byte[]> getCover(@PathVariable long albumId) throws IOException, URISyntaxException {
        Optional<Blob> maybeCoverBlob = this.blobStore.get(this.getCoverBlobName(albumId));
        Blob coverBlob = (Blob)maybeCoverBlob.orElseGet(this::buildDefaultCoverBlob);
        byte[] imageBytes = IOUtils.toByteArray(coverBlob.inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(coverBlob.contentType));
        headers.setContentLength((long)imageBytes.length);
        return new HttpEntity(imageBytes, headers);
    }

    private void tryToUploadCover(@PathVariable Long albumId, @RequestParam("file") MultipartFile uploadedFile) throws IOException {
        Blob coverBlob = new Blob(this.getCoverBlobName(albumId), uploadedFile.getInputStream(), uploadedFile.getContentType());
        this.blobStore.put(coverBlob);
    }

    private Blob buildDefaultCoverBlob() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("resources/default-cover.jpg");
        return new Blob("default-cover", input, "image/jpeg");
    }

    private String getCoverBlobName(@PathVariable long albumId) {
        return String.format("covers/%d", albumId);
    }
}
