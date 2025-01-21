package org.herovole.blogproj.application.auth.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.AdminUserLoginRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginAdminInput {

    public static LoginAdminInput of(IPv4Address ip, FormContent formContent) {
        return new LoginAdminInput(
                AdminUserLoginRequest.fromFormContent(ip, formContent)
        );
    }

    private final AdminUserLoginRequest adminUserLoginRequest;
}
