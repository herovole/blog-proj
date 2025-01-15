package org.herovole.blogproj.application.auth.verifyorganicity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class VerifyOrganicityOutput {
    private final boolean isHuman;

    public boolean isHuman() {
        return this.isHuman;
    }
}
