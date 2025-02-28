package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.image.Images;
import org.herovole.blogproj.infra.filesystem.ImageAsMultipartFile;
import org.herovole.blogproj.infra.filesystem.ImageAsS3HeadResponse;
import org.herovole.blogproj.infra.filesystem.ImageAsS3Object;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageDatasourceAWSS3 implements ImageDatasource {

    private static final AccessKey DIRECTORY_ARTICLE = AccessKeyAsPath.valueOf("articles");
    private static final int MAX_OBJECT_AMOUNT = 1000; // This is the maximum number S3 supports.
    private final S3Client s3Client;
    private final String bucketName;

    public ImageDatasourceAWSS3(S3Client s3client, String bucketName) {
        this.s3Client = s3client;
        this.bucketName = bucketName;
    }

    // ex. https://blog2025-stg-s3-public.s3.ap-northeast-1.amazonaws.com/large.jpg
    @Override
    public String imageResourcePrefix() {
        return "https://<bucketName>.s3.ap-northeast-1.amazonaws.com".replace("<bucketName>", bucketName);
    }

    @Override
    public void persist(AccessKey key, Image image) throws IOException {
        if (!(image instanceof ImageAsMultipartFile)) throw new IllegalStateException("incompatible Image type.");
        MultipartFile imageFile = ((ImageAsMultipartFile) image).toMultipartFile();
        AccessKey destKey = DIRECTORY_ARTICLE.appendWithSlash(key);

        if (!this.findByName(destKey).isEmpty()) throw new IOException("Declared file has already existed.");

        // Upload to S3
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(destKey.memorySignature())
                        .contentType(imageFile.getContentType())
                        .build(),
                RequestBody.fromBytes(imageFile.getBytes())
        );
    }

    @Override
    public void remove(AccessKey key) throws IOException {
        AccessKey destKey = DIRECTORY_ARTICLE.appendWithSlash(key);
        if (this.findByName(destKey).isEmpty()) throw new IOException("Declared file doesn't exist: " + destKey);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(destKey.memorySignature())
                .build();

        s3Client.deleteObject(deleteRequest);
    }

    @Override
    public Images searchSortedByTimestampDesc(PagingRequest request) throws IOException {
        List<S3Object> objects = new ArrayList<>();
        String continuationToken = null;

        do {
            ListObjectsV2Request.Builder requestBuilder = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(DIRECTORY_ARTICLE.memorySignature() + "/") // List only objects in "dir1/"
                    .maxKeys(MAX_OBJECT_AMOUNT);

            if (continuationToken != null) {
                requestBuilder.continuationToken(continuationToken);
            }

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(requestBuilder.build());
            objects.addAll(listResponse.contents());
            continuationToken = listResponse.nextContinuationToken(); // Get next page token

        } while (continuationToken != null); // Continue if there are more results
        Image[] images = objects.stream().filter(object -> !object.key().endsWith("/"))
                .map(ImageAsS3Object::of).toArray(Image[]::new);
        return Images.of(images).sortByTimestampDesc();
    }

    @Override
    public Image findByName(AccessKey name) throws IOException {
        AccessKey destKey = DIRECTORY_ARTICLE.appendWithSlash(name);
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(destKey.memorySignature())
                    .build();
            HeadObjectResponse headResponse = s3Client.headObject(headRequest);
            return ImageAsS3HeadResponse.of(name, headResponse);
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return Image.empty();
            } else {
                throw new IOException(e.getMessage());
            }
        }
    }

    @Override
    public int getTotal() throws IOException {
        int totalCount = 0;
        String continuationToken = null;

        do {
            ListObjectsV2Request.Builder requestBuilder = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(DIRECTORY_ARTICLE.memorySignature() + "/") // List only objects in "dir1/"
                    .maxKeys(MAX_OBJECT_AMOUNT);

            if (continuationToken != null) {
                requestBuilder.continuationToken(continuationToken);
            }

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(requestBuilder.build());
            totalCount += listResponse.keyCount(); // Add count of current page
            continuationToken = listResponse.nextContinuationToken(); // Get next page token

        } while (continuationToken != null); // Continue if there are more results

        return totalCount;
    }
}
