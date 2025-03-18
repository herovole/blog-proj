package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.FormContent;

@Getter
@Builder
public class AdminUserRegistrationRequest {

    public static AdminUserRegistrationRequest fromFormContent(FormContent formContent) {
        return AdminUserRegistrationRequest.builder()
                .userName(UserName.fromFormContentUserName(formContent))
                .role(Role.fromFormContentRole(formContent))
                .password(Password.fromFormContentPassword(formContent))
                .eMailAddress(EMailAddress.fromFormContentEMailAddress(formContent))
                .build();
    }

    private final UserName userName;
    private final Role role;
    private final Password password;
    private final EMailAddress eMailAddress;
}
