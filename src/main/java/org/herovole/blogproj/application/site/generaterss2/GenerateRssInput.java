package org.herovole.blogproj.application.site.generaterss2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerateRssInput {

    public static GenerateRssInput fromFormContent(FormContent formContent) {
        return new GenerateRssInput(
                IntegerId.fromFormContentVersion(formContent));
    }

    private final IntegerId version;
}
