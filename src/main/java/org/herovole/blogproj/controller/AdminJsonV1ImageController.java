package org.herovole.blogproj.controller;

import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageAsMultipartFile;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.entrypoint.property.LocalProperty;
import org.herovole.blogproj.infra.filesystem.LocalFiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
public class AdminJsonV1ImageController {

    private final ImageDatasource imageDatasource;

    public AdminJsonV1ImageController(LocalProperty localProperty) throws IOException {
        localProperty.setUpLocalFileSystemReader();
        this.imageDatasource = localProperty.buildImageDatasourceLocalFs();
    }

    @GetMapping
    public ResponseEntity<String[]> getImages(
            @RequestParam("page") int page,
            @RequestParam("number") int number
    ) {
        System.out.println("endpoint : get");
        try {
            PagingRequest pagingRequest = PagingRequest.of(page, number);
            LocalFiles images = imageDatasource.searchSortedByTimestampDesc(pagingRequest);
            String[] imageNames = images.getFileNames();
            return ResponseEntity.ok(imageNames);
        } catch (DomainInstanceGenerationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[]{"Error: " + e.getMessage()});
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new String[]{"Internal Server Error: " + e.getMessage()});
        }
    }

    @PostMapping
    public ResponseEntity<String> postImages(@RequestPart("image") MultipartFile file) {
        System.out.println("endpoint : post");
        System.out.println("/api/v1/images");
        if (!file.isEmpty()) {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
        }

        Image image = ImageAsMultipartFile.of(file);
        AccessKey fileName = AccessKeyAsPath.nameOf(image);
        try {
            imageDatasource.persist(fileName, image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body("Data received successfully");
    }

}
