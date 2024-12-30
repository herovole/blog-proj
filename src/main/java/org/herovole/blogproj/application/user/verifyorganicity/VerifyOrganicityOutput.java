package org.herovole.blogproj.application.user.verifyorganicity;

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
