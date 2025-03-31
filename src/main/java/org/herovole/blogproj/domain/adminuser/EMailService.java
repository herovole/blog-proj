package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;

import java.io.IOException;

public interface EMailService {
    void sendVerificationCode(IPv4Address ip, EMailAddress emailAddress, VerificationCode verificationCode) throws IOException;
}
