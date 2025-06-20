package org.herovole.blogproj;

import lombok.Data;
import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "spring.config.local")
public class LocalProperty {

    private static final Logger logger = LoggerFactory.getLogger(LocalProperty.class.getSimpleName());
    private String boundary;
    private String config;

    @Bean
    LocalFileSystem buildLocalFileSystem() throws HazardousFileSystemNodeException {
        logger.info("Bean component : {}", LocalFileSystem.class.getSimpleName());
        return new LocalFileSystem(boundary);
    }

    @Bean
    public ConfigFile getConfigFile() throws IOException {
        logger.info("Bean component : {}", ConfigFile.class.getSimpleName());
        logger.info("boundary {}", boundary);
        logger.info("config {}", config);
        ConfigFile file = ConfigFile.of(config, this.buildLocalFileSystem());
        file.buildOwnerUserRegistrationRequest();
        return file;
    }

}
