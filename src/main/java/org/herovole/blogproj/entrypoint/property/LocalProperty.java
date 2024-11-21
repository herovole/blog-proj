package org.herovole.blogproj.entrypoint.property;

import lombok.Data;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.herovole.blogproj.infra.datasource.ImageDatasourceLocalFs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Data
@Component
@ConfigurationProperties(prefix = "spring.config.local")
public class LocalProperty {
    private String boundary;
    private String images;

    public void setUpLocalFileSystemReader() throws HazardousFileSystemNodeException {
        LocalFileSystem.buildSingleton(boundary);
    }

    public ImageDatasource buildImageDatasourceLocalFs() throws IOException {
        LocalDirectory imageDirectory = LocalDirectory.of(AccessKeyAsPath.valueOf(images));
        return new ImageDatasourceLocalFs(imageDirectory);
    }
}
