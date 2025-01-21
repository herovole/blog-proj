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
@ConfigurationProperties(prefix = "spring.config.local")
@Configuration
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
    ConfigFile getConfigFile() throws IOException {
        logger.info("Bean component : {}", ConfigFile.class.getSimpleName());
        return ConfigFile.of(config, this.buildLocalFileSystem());
    }

}
