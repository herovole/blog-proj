package org.herovole.blogproj.domain.adminuser;

public interface AdminUserDatasource {
    AdminUser find(UserName userName);

    AdminUser find(AccessToken accessToken);
}
