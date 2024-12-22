package org.herovole.blogproj.application.user.verifyorganicity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class VerifyOrganicityOutput {
    private final Boolean isHuman;

    public boolean isHuman() {
        return isHuman != null && isHuman;
    }

    public boolean isSuccessful() {
        return isHuman != null;
    }

    record Json(
            boolean isHuman,
            boolean isSuccessful
    ) {
    }

    public VerifyOrganicityOutput.Json toJsonModel() {
        return new VerifyOrganicityOutput.Json(isHuman(), isSuccessful());
    }
}

