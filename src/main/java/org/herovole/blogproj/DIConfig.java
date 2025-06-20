package org.herovole.blogproj;

import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentDurationConfig;
import org.herovole.blogproj.domain.meta.SiteInformation;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.adminuser.EMailService;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.herovole.blogproj.infra.hibernate.AppSessionFactoryHibernate;
import org.herovole.blogproj.infra.service.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ForwardedHeaderFilter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@Configuration
public class DIConfig {
    private final LocalFileSystem localFileSystem;
    private final ConfigFile configFile;
    //private final JavaMailSender javaMailSender;

    @Autowired
    public DIConfig(LocalFileSystem localFileSystem, ConfigFile configFile) {
        this.localFileSystem = localFileSystem;
        this.configFile = configFile;
    }

    /*
    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
     */

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> bean = new FilterRegistrationBean<>(new ForwardedHeaderFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
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
    public SiteInformation getSiteInformation() {
        return configFile.getSiteInformation();
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

    @Bean("articleTransactionalDatasourceRss2Feed")
    //@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss2Feed() throws IOException {
        LocalFile rssFeed = LocalFile.of(configFile.getRssFeedFile(), localFileSystem);
        return new ArticleTransactionalDatasourceRss2(
                rssFeed,
                configFile.getSiteInformation());
    }

    @Bean("articleTransactionalDatasourceRss2")
    //@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss2() throws IOException {
        LocalFile rss2Xml = LocalFile.of(configFile.getRssXml20File(), localFileSystem);
        return new ArticleTransactionalDatasourceRss2(
                rss2Xml,
                configFile.getSiteInformation());
    }

    @Bean("articleTransactionalDatasourceRss1")
    //@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss1() throws IOException {
        LocalFile rss1Xml = LocalFile.of(configFile.getRssXml10File(), localFileSystem);
        return new ArticleTransactionalDatasourceRss1(
                rss1Xml,
                configFile.getSiteInformation());
    }

    @Bean("articleTransactionalDatasourceRss2German")
    //@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss2German() throws IOException {
        LocalFile rss2XmlGerman = LocalFile.of(configFile.getRssXmlGerman20File(), localFileSystem);
        return new ArticleTransactionalDatasourceRss2(
                rss2XmlGerman,
                configFile.getSiteInformation());
    }

    @Bean("articleTransactionalDatasourceRss1German")
    //@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public ArticleTransactionalDatasource buildArticleTransactionalDatasourceRss1German() throws IOException {
        LocalFile rss1XmlGerman = LocalFile.of(configFile.getRssXmlGerman10File(), localFileSystem);
        return new ArticleTransactionalDatasourceRss1(
                rss1XmlGerman,
                configFile.getSiteInformation());
    }

    @Bean
    public PostUserCommentDurationConfig buildPostUserCommentDurationConfig() {
        return configFile.getCommentDurationConfig();
    }

    // Not in Use
    //public EMailService buildEMailServiceSpringMail() {
    //    return new EMailServiceSpringMail(this.javaMailSender, configFile.getSiteInformation());
    //}

    @Bean
    public EMailService buildEMailService() {
        return this.configFile.buildEMailServiceAmazonSES();
    }
}
