package org.herovole.blogproj.domain;

import lombok.Builder;
import org.herovole.blogproj.domain.source.SourceUrl;

@Builder
public class SiteInformation {
    private final String siteDomain;
    private final String imageDomain;
    private final String siteNameJp;
    private final String siteNameEn;
    private final String siteDescription;
    private final String siteCopyright;
    private final String siteLanguage;

    public SourceUrl getSiteTopUrl() {
        if (siteDomain == null || siteDomain.isEmpty() || siteDomain.isBlank()) {
            throw new IllegalStateException();
        }
        return SourceUrl.valueOf("https://" + siteDomain);
    }

    public SourceUrl getArticlesUrl() {
        return SourceUrl.valueOf(this.getSiteTopUrl().memorySignature() + "/articles");
    }

    public SourceUrl getImageTopUrl() {
        if (imageDomain == null || imageDomain.isEmpty() || imageDomain.isBlank()) {
            throw new IllegalStateException();
        }
        return SourceUrl.valueOf("https://" + imageDomain);
    }

    public String getSiteNameJp() {
        return this.siteNameJp;
    }

    public String getSiteNameEn() {
        return this.siteNameEn;
    }

    public String getSiteDescription() {
        return this.siteDescription;
    }

    public String getSiteCopyright() {
        return this.siteCopyright;
    }

    public String getSiteLanguage() {
        return this.siteLanguage;
    }
}
