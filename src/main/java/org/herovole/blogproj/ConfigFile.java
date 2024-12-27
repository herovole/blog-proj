package org.herovole.blogproj;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;

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
    private static final String CONFIG_KEY_IMAGES = "images";
    private static final String CONFIG_KEY_COMMENT_BLACKLIST = "comment_blacklist";
    private static final String CONFIG_KEY_G_RECAPTCHA_SECRET_KEY = "g_recaptcha_sercret_key";
    private static final String CONFIG_KEY_DAILY_USER_ID_KEY0 = "daily_user_id_key0";

    private final Map<String, String> configs;

    String getImageDirectoryPath() {
        return this.configs.get(CONFIG_KEY_IMAGES);
    }

    String getCommentBlacklistFilePath() {
        return this.configs.get(CONFIG_KEY_COMMENT_BLACKLIST);
    }

    String getGoogleReCaptchaSecretKey() {
        return this.configs.get(CONFIG_KEY_G_RECAPTCHA_SECRET_KEY);
    }

    String getDailyUserIdKey0() {
        return this.configs.get(CONFIG_KEY_DAILY_USER_ID_KEY0);
    }
}
