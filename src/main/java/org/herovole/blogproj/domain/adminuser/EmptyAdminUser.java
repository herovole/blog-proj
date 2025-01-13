package org.herovole.blogproj.domain.adminuser;

public class EmptyAdminUser implements AdminUser {
    @Override
    public UserName getUserName() {
        return UserName.empty();
    }

    @Override
    public Role getRole() {
        return Role.NONE;
    }
}
