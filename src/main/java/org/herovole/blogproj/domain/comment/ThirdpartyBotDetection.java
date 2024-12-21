package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.domain.IPv4Address;

import java.io.IOException;

public interface ThirdpartyBotDetection {
    float receiveProbabilityOfBeingHuman(String verificationToken, IPv4Address iPv4Address) throws IOException, InterruptedException;
}
