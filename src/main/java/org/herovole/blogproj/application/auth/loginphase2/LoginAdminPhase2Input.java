package org.herovole.blogproj.application.auth.loginphase2;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.Password;
import org.herovole.blogproj.domain.adminuser.UserName;
import org.herovole.blogproj.domain.adminuser.VerificationCode;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginAdminPhase2Input {

    public static LoginAdminPhase2Input fromFormContent(IPv4Address ip, FormContent formContent) {
        return LoginAdminPhase2Input.builder()
                .userName(UserName.fromFormContentUserName(formContent))
                .password(Password.fromFormContentPassword(formContent))
                .ip(ip)
                .verificationCode(VerificationCode.fromFormContentVerificationCode(formContent))
                .build();
    }

    private final UserName userName;
    private final Password password;
    private final IPv4Address ip;
    private final VerificationCode verificationCode;
}
