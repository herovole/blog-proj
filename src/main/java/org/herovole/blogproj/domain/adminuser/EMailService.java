package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.EMailAddress;

public interface EMailService {
    void sendVerificationCode(EMailAddress emailAddress, VerificationCode verificationCode);
}
