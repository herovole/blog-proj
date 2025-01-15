package org.herovole.blogproj;

import lombok.Data;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.infra.datasource.AccessTokenFactoryJwt;
import org.herovole.blogproj.infra.datasource.DailyUserIdFactoryImpl;
import org.herovole.blogproj.infra.datasource.GoogleReCaptchaResultServer;
import org.herovole.blogproj.infra.datasource.ImageDatasourceLocalFs;
import org.herovole.blogproj.infra.datasource.TextBlackListLocalFile;
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

@Data
@Component
@ConfigurationProperties(prefix = "spring.config.local")
@Configuration
public class LocalProperty {

    private static final Logger logger = LoggerFactory.getLogger(LocalProperty.class.getSimpleName());
    private String boundary;
    private String config;

    private LocalFileSystem buildLocalFileSystem() throws HazardousFileSystemNodeException {
        logger.info("Bean component : {}", LocalFileSystem.class.getSimpleName());
        return new LocalFileSystem(boundary);
    }

    private ConfigFile getConfigFile() throws IOException {
        logger.info("Bean component : {}", ConfigFile.class.getSimpleName());
        return ConfigFile.of(config, this.buildLocalFileSystem());
    }

    @Bean
    public ImageDatasource buildImageDatasourceLocalFs() throws IOException {
        LocalFileSystem localFileSystem = this.buildLocalFileSystem();
        LocalDirectory imageDirectory = LocalDirectory.of(
                this.getConfigFile().getImageDirectoryPath(),
                localFileSystem
        );
        return new ImageDatasourceLocalFs(imageDirectory);
    }

    @Bean
    public TextBlackList buildBlacklists() throws IOException {
        LocalFileSystem localFileSystem = this.buildLocalFileSystem();
        LocalFile commentBlacklistFile = LocalFile.of(
                this.getConfigFile().getCommentBlacklistFilePath(),
                localFileSystem
        );
        LocalFile systemBlacklistFile = LocalFile.of(
                this.getConfigFile().getSystemBlacklistFilePath(),
                localFileSystem
        );
        return new TextBlackListLocalFile.Builder()
                .setHumanBlackListFile(commentBlacklistFile)
                .setSystemBlackListFile(systemBlacklistFile)
                .build();
    }

    @Bean
    public GoogleReCaptchaResultServer buildGoogleReCaptchaResultServer() throws IOException {
        return GoogleReCaptchaResultServer.of(this.getConfigFile().getGoogleReCaptchaSecretKey());
    }

    @Bean
    public DailyUserIdFactory buildDailyUserIdFactory() throws IOException {
        return DailyUserIdFactoryImpl.of(this.getConfigFile().getDailyUserIdKey0());
    }

    @Bean
    public AccessTokenFactory buildAccessTokenFactory() throws IOException {
        return AccessTokenFactoryJwt.of(this.getConfigFile().getHoursAdminTokenExpires());
    }
}
