package org.herovole.blogproj.application.auth.registeruser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.adminuser.AdminUserRegistrationRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserInput {

    public static RegisterUserInput of(FormContent formContent) {
        return new RegisterUserInput(
                AdminUserRegistrationRequest.fromFormContent(formContent)
        );
    }

    public static RegisterUserInput of(AdminUserRegistrationRequest adminUserRegistrationRequest) {
        return new RegisterUserInput(adminUserRegistrationRequest);
    }

    private final AdminUserRegistrationRequest adminUserRegistrationRequest;
}
