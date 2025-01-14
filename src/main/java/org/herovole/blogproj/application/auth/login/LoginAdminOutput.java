package org.herovole.blogproj.application.auth.login;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.article.Article;

@RequiredArgsConstructor
public class LoginAdminOutput {
    private final AccessToken accessToken;
}

