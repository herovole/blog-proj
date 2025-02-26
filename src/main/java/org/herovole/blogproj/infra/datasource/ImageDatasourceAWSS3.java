package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.image.Images;
import org.herovole.blogproj.infra.filesystem.ImageAsLocalFile;
import org.herovole.blogproj.infra.filesystem.ImageAsMultipartFile;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFiles;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

public class ImageDatasourceAWSS3 implements ImageDatasource {

    private static final String PROTOCOL = "https://";
    private static final AccessKey DIRECTORY_ARTICLE = AccessKeyAsPath.valueOf("article");
    private final S3Client s3Client;
    private final String bucketName;

    public ImageDatasourceAWSS3(S3Client s3client, String bucketName) {
        this.s3Client = s3client;
        this.bucketName = bucketName;
    }

    private boolean hasObject(AccessKey key) throws IOException {
        AccessKey destKey = DIRECTORY_ARTICLE.appendWithSlash(key);
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(destKey.memorySignature())
                    .build();
            s3Client.headObject(headRequest);
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            } else {
                throw new IOException(e.getMessage());
            }
        }
    }

    @Override
    public void persist(AccessKey key, Image image) throws IOException {
        if (!(image instanceof ImageAsMultipartFile)) throw new IllegalStateException("incompatible Image type.");
        MultipartFile imageFile = ((ImageAsMultipartFile) image).toMultipartFile();
        AccessKey destKey = DIRECTORY_ARTICLE.appendWithSlash(key);

        if (this.hasObject(destKey)) throw new IOException("Declared file has already existed.");

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
        if (!this.hasObject(destKey)) throw new IOException("Declared file doesn't exist: " + destKey);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(destKey.memorySignature())
                .build();

        s3Client.deleteObject(deleteRequest);
    }

    @Override
    public Images searchSortedByTimestampDesc(PagingRequest request) throws IOException {
        LocalFiles files = parentDirectory.getFiles().sortByTimestampDesc().get(request);
        return files.asImages();
    }

    @Override
    public Image findByName(AccessKey name) throws IOException {
        LocalFile file = parentDirectory.find(name);
        return ImageAsLocalFile.of(file);
    }

    @Override
    public int getTotal() throws IOException {
        return parentDirectory.getFiles().getTotal();
    }
}
