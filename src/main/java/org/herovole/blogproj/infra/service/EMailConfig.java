package org.herovole.blogproj.infra.service;

import lombok.Builder;

@Builder
public class EMailConfig {
    private final String smtpHost;
    private final String smtpPort;
    private final String smtpUser;
    private final String smtpPassword;
    private final String mailFrom;
}
