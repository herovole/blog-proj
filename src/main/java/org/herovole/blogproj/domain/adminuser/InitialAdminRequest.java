package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;

@Getter
@Builder
public class InitialAdminRequest {

    public static InitialAdminRequest fromFormContent(IPv4Address ip, FormContent formContent) {
        return InitialAdminRequest.builder()
                .userName(UserName.fromFormContentUserName(formContent))
                .password(Password.fromFormContentPassword(formContent))
                .ip(ip)
                .build();
    }

    private final UserName userName;
    private final Password password;
    private final IPv4Address ip;
}
