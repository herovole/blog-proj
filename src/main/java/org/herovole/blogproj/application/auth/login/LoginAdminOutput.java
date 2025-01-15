package org.herovole.blogproj.application.auth.login;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.adminuser.AccessToken;

@RequiredArgsConstructor
public class LoginAdminOutput {
    private final AccessToken accessToken;

    public Json toJsonModel() {
        return new Json(accessToken.memorySignature());
    }

    public record Json(String accessToken) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}

