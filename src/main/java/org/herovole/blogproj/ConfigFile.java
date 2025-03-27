package org.herovole.blogproj;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentDurationConfig;
import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.SiteInformation;
import org.herovole.blogproj.domain.adminuser.AdminUserRegistrationRequest;
import org.herovole.blogproj.domain.adminuser.Password;
import org.herovole.blogproj.domain.adminuser.Role;
import org.herovole.blogproj.domain.adminuser.UserName;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.herovole.blogproj.infra.service.EMailServiceAmazonSES;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigFile {

    static ConfigFile of(String path, LocalFileSystem fs) throws IOException {
        LocalFile localFile = LocalFile.of(path, fs);
        final Map<String, String> configs = new HashMap<>();

        localFile.readLines().forEach(line -> {
            String[] config = line.split(CONFIG_SEPARATOR);
            if (config.length == 2) {
                configs.put(config[0], config[1]);
            }
        });
        return new ConfigFile(configs);
    }

    private static final String CONFIG_SEPARATOR = "=";
    private static final String CONFIG_KEY_IMAGE_LOCAL = "img_local";
    private static final String CONFIG_KEY_IMAGE_DOMAIN = "img_domain";
    private static final String CONFIG_KEY_COMMENT_BLACKLIST = "comment_blacklist";
    private static final String CONFIG_KEY_SYSTEM_BLACKLIST = "system_blacklist";
    private static final String CONFIG_KEY_COMMENT_DURATION_SAME_ARTICLE = "comment_duration_same_article_seconds";
    private static final String CONFIG_KEY_COMMENT_DURATION_GENERAL = "comment_duration_general_seconds";
    private static final String CONFIG_KEY_G_RECAPTCHA_SECRET_KEY = "g_recaptcha_sercret_key";
    private static final String CONFIG_KEY_DAILY_USER_ID_KEY0 = "daily_user_id_key0";
    private static final String CONFIG_KEY_HOURS_ADMIN_TOKEN_EXPIRES = "hours_admin_token_expires";
    private static final String CONFIG_KEY_OWNER_USER = "owner_user";
    private static final String CONFIG_KEY_OWNER_PASSWORD = "owner_password";
    private static final String CONFIG_KEY_OWNER_EMAIL = "owner_email";
    private static final String CONFIG_KEY_AWS_ACCESS_KEY = "aws_access_key";
    private static final String CONFIG_KEY_AWS_SECRET_ACCESS_KEY = "aws_secret_access_key";
    private static final String CONFIG_KEY_AWS_PUBLIC_RESOURCES_BUCKET = "aws_public_resources_bucket";
    private static final String CONFIG_KEY_ENVIRONMENT = "environment";
    private static final String CONFIG_KEY_SITE_DOMAIN = "site_domain";
    private static final String CONFIG_KEY_SITE_NAME_JP = "site_name_jp";
    private static final String CONFIG_KEY_SITE_NAME_EN = "site_name_en";
    private static final String CONFIG_KEY_SITE_DESCRIPTION = "site_description";
    private static final String CONFIG_KEY_SITE_LANG = "site_lang";
    private static final String CONFIG_KEY_SITE_COPYRIGHT = "site_copyright";
    private static final String CONFIG_KEY_RSS_XML = "rss_output";
    private static final String CONFIG_KEY_SMTP_HOST = "smtp_host";
    private static final String CONFIG_KEY_SMTP_PORT = "smtp_port";
    private static final String CONFIG_KEY_SMTP_USER = "smtp_user";
    private static final String CONFIG_KEY_SMTP_PASSWORD = "smtp_password";
    private static final String CONFIG_KEY_MAIL_FROM = "mail_from";

    private final Map<String, String> configs;

    String getImageDirectoryPath() {
        return this.configs.get(CONFIG_KEY_IMAGE_LOCAL);
    }

    String getCommentBlacklistFilePath() {
        return this.configs.get(CONFIG_KEY_COMMENT_BLACKLIST);
    }

    String getSystemBlacklistFilePath() {
        return this.configs.get(CONFIG_KEY_SYSTEM_BLACKLIST);
    }

    PostUserCommentDurationConfig getCommentDurationConfig() {
        return new PostUserCommentDurationConfig(
                Integer.parseInt(this.configs.get(CONFIG_KEY_COMMENT_DURATION_SAME_ARTICLE)),
                Integer.parseInt(this.configs.get(CONFIG_KEY_COMMENT_DURATION_GENERAL))
        );
    }

    String getGoogleReCaptchaSecretKey() {
        return this.configs.get(CONFIG_KEY_G_RECAPTCHA_SECRET_KEY);
    }

    String getDailyUserIdKey0() {
        return this.configs.get(CONFIG_KEY_DAILY_USER_ID_KEY0);
    }

    int getHoursAdminTokenExpires() {
        return Integer.parseInt(this.configs.get(CONFIG_KEY_HOURS_ADMIN_TOKEN_EXPIRES));
    }

    private UserName getOwnerUser() {
        return UserName.valueOf(this.configs.get(CONFIG_KEY_OWNER_USER));
    }

    private Password getOwnerPassword() {
        return Password.valueOf(this.configs.get(CONFIG_KEY_OWNER_PASSWORD));
    }

    private EMailAddress getOwnerEMail() {
        return EMailAddress.valueOf(this.configs.get(CONFIG_KEY_OWNER_EMAIL));
    }

    public AdminUserRegistrationRequest buildOwnerUserRegistrationRequest() {
        return AdminUserRegistrationRequest.builder()
                .userName(this.getOwnerUser())
                .eMailAddress(this.getOwnerEMail())
                .password(this.getOwnerPassword())
                .role(Role.OWNER)
                .build();
    }

    public String getAwsAccessKey() {
        return this.configs.get(CONFIG_KEY_AWS_ACCESS_KEY);
    }

    public String getAwsSecretAccessKey() {
        return this.configs.get(CONFIG_KEY_AWS_SECRET_ACCESS_KEY);
    }

    public String getAwsPublicResourcesBucket() {
        return this.configs.get(CONFIG_KEY_AWS_PUBLIC_RESOURCES_BUCKET);
    }

    private String getEnvironment() {
        return this.configs.get(CONFIG_KEY_ENVIRONMENT);
    }

    private String getSiteDomain() {
        return this.configs.get(CONFIG_KEY_SITE_DOMAIN);
    }

    private String getImageDomain() {
        return this.configs.get(CONFIG_KEY_IMAGE_DOMAIN);
    }

    private String getSiteNameJp() {
        return this.configs.get(CONFIG_KEY_SITE_NAME_JP);
    }

    private String getSiteNameEn() {
        return this.configs.get(CONFIG_KEY_SITE_NAME_EN);
    }

    private String getSiteDescription() {
        return this.configs.get(CONFIG_KEY_SITE_DESCRIPTION);
    }

    private String getSiteLanguage() {
        return this.configs.get(CONFIG_KEY_SITE_LANG);
    }

    private String getSiteCopyright() {
        return this.configs.get(CONFIG_KEY_SITE_COPYRIGHT);
    }

    public SiteInformation getSiteInformation() {
        return SiteInformation.builder()
                .environment(this.getEnvironment())
                .siteDomain(this.getSiteDomain())
                .imageDomain(this.getImageDomain())
                .siteNameJp(this.getSiteNameJp())
                .siteNameEn(this.getSiteNameEn())
                .siteDescription(this.getSiteDescription())
                .siteLanguage(this.getSiteLanguage())
                .siteCopyright(this.getSiteCopyright())
                .build();
    }

    public String getRssXmlFile() {
        return this.configs.get(CONFIG_KEY_RSS_XML);
    }

    public String getSmtpHost() {
        return this.configs.get(CONFIG_KEY_SMTP_HOST);
    }

    public String getSmtpPort() {
        return this.configs.get(CONFIG_KEY_SMTP_PORT);
    }

    public String getSmtpUser() {
        return this.configs.get(CONFIG_KEY_SMTP_USER);
    }

    public String getSmtpPassword() {
        return this.configs.get(CONFIG_KEY_SMTP_PASSWORD);
    }

    public String getMailFrom() {
        return this.configs.get(CONFIG_KEY_MAIL_FROM);
    }

    public EMailServiceAmazonSES buildEMailServiceAmazonSES() {
        return EMailServiceAmazonSES.builder()
                .siteInformation(this.getSiteInformation())
                .smtpHost(this.getSmtpHost())
                .smtpPort(this.getSmtpPort())
                .smtpUser(this.getSmtpUser())
                .smtpPassword(this.getSmtpPassword())
                .mailFrom(this.getMailFrom())
                .build();
    }
}
