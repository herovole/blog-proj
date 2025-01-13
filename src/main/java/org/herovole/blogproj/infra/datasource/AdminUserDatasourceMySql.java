package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.herovole.blogproj.domain.adminuser.ContinualAdminRequest;
import org.herovole.blogproj.domain.adminuser.InitialAdminRequest;
import org.herovole.blogproj.infra.jpa.entity.MAdminUser;
import org.herovole.blogproj.infra.jpa.repository.MAdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminUserDatasource")
public class AdminUserDatasourceMySql implements AdminUserDatasource {
    protected final MAdminUserRepository mAdminUserRepository;

    @Autowired
    public AdminUserDatasourceMySql(MAdminUserRepository mAdminUserRepository) {
        this.mAdminUserRepository = mAdminUserRepository;
    }

    @Override
    public AdminUser find(InitialAdminRequest request) {
        List<MAdminUser> users = mAdminUserRepository.findByUserName(request.getUserName().memorySignature());
        if (users.isEmpty()) return AdminUser.empty();
        if (users.size() > 1) throw new IllegalArgumentException("Multiple users found for the name " + request.getUserName().letterSignature());
        return users.get(0).toDomainObj();
    }

    @Override
    public AdminUser find(ContinualAdminRequest request) {
        List<MAdminUser> users = mAdminUserRepository.findByAccessToken(
                request.getAccessToken().memorySignature()
        );
        if (users.isEmpty()) return AdminUser.empty();
        if (users.size() > 1) throw new IllegalArgumentException("Multiple users found for the same token");
        return users.get(0).toDomainObj();
    }
}
