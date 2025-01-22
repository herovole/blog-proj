package org.herovole.blogproj;

import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.infra.datasource.AccessTokenFactoryJwt;
import org.herovole.blogproj.infra.datasource.DailyUserIdFactoryImpl;
import org.herovole.blogproj.infra.datasource.GoogleReCaptchaResultServer;
import org.herovole.blogproj.infra.datasource.ImageDatasourceLocalFs;
import org.herovole.blogproj.infra.datasource.TextBlackListLocalFile;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.herovole.blogproj.infra.hibernate.AppSessionFactoryHibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DIConfig {
    private final LocalFileSystem localFileSystem;
    private final ConfigFile configFile;

    @Autowired
    public DIConfig(LocalFileSystem localFileSystem, ConfigFile configFile) {
        this.localFileSystem = localFileSystem;
        this.configFile = configFile;
    }

    @Bean
    public AppSessionFactory appSessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        return new AppSessionFactoryHibernate(sessionFactory);
    }

    @Bean
    public ImageDatasource buildImageDatasourceLocalFs() throws IOException {
        LocalDirectory imageDirectory = LocalDirectory.of(
                configFile.getImageDirectoryPath(),
                localFileSystem
        );
        return new ImageDatasourceLocalFs(imageDirectory);
    }

    @Bean
    public TextBlackList buildBlacklists() throws IOException {
        LocalFile commentBlacklistFile = LocalFile.of(
                configFile.getCommentBlacklistFilePath(),
                localFileSystem
        );
        LocalFile systemBlacklistFile = LocalFile.of(
                configFile.getSystemBlacklistFilePath(),
                localFileSystem
        );
        return new TextBlackListLocalFile.Builder()
                .setHumanBlackListFile(commentBlacklistFile)
                .setSystemBlackListFile(systemBlacklistFile)
                .build();
    }

    @Bean
    public GoogleReCaptchaResultServer buildGoogleReCaptchaResultServer() {
        return GoogleReCaptchaResultServer.of(configFile.getGoogleReCaptchaSecretKey());
    }

    @Bean
    public DailyUserIdFactory buildDailyUserIdFactory() {
        return DailyUserIdFactoryImpl.of(configFile.getDailyUserIdKey0());
    }

    @Bean
    public AccessTokenFactory buildAccessTokenFactory() {
        return AccessTokenFactoryJwt.of(configFile.getHoursAdminTokenExpires());
    }
}
