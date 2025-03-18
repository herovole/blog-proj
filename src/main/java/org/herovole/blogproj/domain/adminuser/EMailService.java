package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.EMailAddress;

import java.io.IOException;

public interface EMailService {
    void sendVerificationCode(EMailAddress emailAddress, VerificationCode verificationCode) throws IOException;
}
