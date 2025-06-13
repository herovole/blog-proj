package org.herovole.blogproj.application.site.generaterss2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.meta.RssType;

@ToString
@Getter
@RequiredArgsConstructor
public class GenerateRssInput {

    public static GenerateRssInput fromFormContent(FormContent formContent) {
        return new GenerateRssInput(
                RssType.fromFormContent(formContent));
    }

    private final RssType rssType;
}
