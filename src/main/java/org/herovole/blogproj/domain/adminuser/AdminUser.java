package org.herovole.blogproj.domain.adminuser;

public interface AdminUser {
    static AdminUser empty() {
        return new EmptyAdminUser();
    }

    UserName getUserName();
    Role getRole();
}
