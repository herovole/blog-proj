package org.herovole.blogproj.domain.adminuser;

public interface AdminUserDatasource {
    AdminUser find(InitialAdminRequest request);

    AdminUser find(ContinualAdminRequest request);
}
