package org.herovole.blogproj;

import lombok.Data;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.infra.datasource.ImageDatasourceLocalFs;
import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.config.local")
@Configuration
public class LocalProperty {

    private static final Logger logger = LoggerFactory.getLogger(LocalProperty.class.getSimpleName());
    private String boundary;
    private String images;

    @Bean
    public LocalFileSystem buildLocalFileSystem() {
        logger.info("Bean : {}", LocalFileSystem.class.getSimpleName());
        try {
            return new LocalFileSystem(boundary);
        } catch (HazardousFileSystemNodeException e) {
            logger.info("Error : building {}", LocalFileSystem.class.getSimpleName(), e);
            return null;
        }
    }

    @Bean
    public ImageDatasource buildImageDatasourceLocalFs() {
        LocalDirectory imageDirectory = LocalDirectory.of(images);
        return new ImageDatasourceLocalFs(this.buildLocalFileSystem(), imageDirectory);
    }
}
