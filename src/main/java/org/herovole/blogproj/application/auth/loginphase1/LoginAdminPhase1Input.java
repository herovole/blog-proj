package org.herovole.blogproj.application.auth.loginphase1;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.Password;
import org.herovole.blogproj.domain.adminuser.UserName;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginAdminPhase1Input {

    public static LoginAdminPhase1Input fromFormContent(IPv4Address ip, FormContent formContent) {
        return LoginAdminPhase1Input.builder()
                .userName(UserName.fromFormContentUserName(formContent))
                .password(Password.fromFormContentPassword(formContent))
                .ip(ip)
                .build();
    }

    private final UserName userName;
    private final Password password;
    private final IPv4Address ip;
}
