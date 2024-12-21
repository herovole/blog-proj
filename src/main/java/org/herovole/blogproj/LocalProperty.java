package org.herovole.blogproj;

import lombok.Data;
import org.herovole.blogproj.domain.comment.CommentBlackList;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.infra.datasource.CommentBlackListLocalFile;
import org.herovole.blogproj.infra.datasource.ImageDatasourceLocalFs;
import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Data
@Component
@ConfigurationProperties(prefix = "spring.config.local")
@Configuration
public class LocalProperty {

    private static final Logger logger = LoggerFactory.getLogger(LocalProperty.class.getSimpleName());
    private String boundary;
    private String images;
    private String commentBlacklist;

    private LocalFileSystem buildLocalFileSystem() throws HazardousFileSystemNodeException {
        logger.info("Bean : {}", LocalFileSystem.class.getSimpleName());
        return new LocalFileSystem(boundary);
    }

    @Bean
    public ImageDatasource buildImageDatasourceLocalFs() throws IOException {
        Path directoryPath = Path.of(images);
        LocalFileSystem localFileSystem = this.buildLocalFileSystem();
        LocalDirectory imageDirectory = LocalDirectory.of(directoryPath, localFileSystem);
        return new ImageDatasourceLocalFs(imageDirectory);
    }

    @Bean
    public CommentBlackList buildCommentBlacklist() throws IOException {
        Path filePath = Path.of(commentBlacklist);
        LocalFileSystem localFileSystem = this.buildLocalFileSystem();
        LocalFile localFile = LocalFile.of(filePath, localFileSystem);
        return CommentBlackListLocalFile.of(localFile);
    }
}
