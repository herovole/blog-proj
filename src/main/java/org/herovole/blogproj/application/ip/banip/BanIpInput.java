package org.herovole.blogproj.application.ip.banip;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BanIpInput {
    public static BanIpInput ofFormContent(FormContent formContent) {
        // throw error if negative days cause operational issues.
        IPv4Address iPv4Address = IPv4Address.fromFormContentUserId(formContent);
        return BanIpInput.builder()
                .ip(iPv4Address)
                .days(Integer.parseInt(formContent.getChildren(API_KEY_DAYS_TO_SUSPEND).getValue()))
                .build();
    }

    private static final String API_KEY_DAYS_TO_SUSPEND = "days";

    private final IPv4Address ip;
    private final int days;
}

