package org.herovole.blogproj;

import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.infra.datasource.AccessTokenFactoryJwt;
import org.herovole.blogproj.infra.datasource.ArticleTransactionalDatasourceRss2;
import org.herovole.blogproj.infra.datasource.DailyUserIdFactoryImpl;
import org.herovole.blogproj.infra.datasource.GoogleReCaptchaResultServer;
import org.herovole.blogproj.infra.datasource.ImageDatasourceAWSS3;
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
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.nio.file.Path;

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
    @Profile({"localdocker"})
    public ImageDatasource buildImageDatasourceLocalFs() throws IOException {
        LocalDirectory imageDirectory = LocalDirectory.of(
                configFile.getImageDirectoryPath(),
                localFileSystem
        );
        return new ImageDatasourceLocalFs(imageDirectory);
    }

    @Bean
    @Profile({"local", "staging", "prod"})
    public ImageDatasource buildImageDatasourceS3() throws IOException {
        S3Client s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_1) // Set your region
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                configFile.getAwsAccessKey(),
                                configFile.getAwsSecretAccessKey()
                        )
                )).build();
        return new ImageDatasourceAWSS3(configFile.getSiteInformation(), s3Client, configFile.getAwsPublicResourcesBucket());
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

    @Bean("articleTransactionalDatasourceRss2")
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss2() throws IOException {
        LocalFile rss2Xml = LocalFile.of(configFile.getRssXmlFile(), localFileSystem);
        return new ArticleTransactionalDatasourceRss2(rss2Xml, configFile.getSiteInformation());
    }
}
