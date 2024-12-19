package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.PublicUserDatasource;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.herovole.blogproj.infra.jpa.entity.EPublicUser;
import org.herovole.blogproj.infra.jpa.repository.EPublicUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("publicUserDatasource")
public class PublicUserDatasourceMySql implements PublicUserDatasource {
    protected final EPublicUserRepository ePublicUserRepository;

    @Autowired
    public PublicUserDatasourceMySql(EPublicUserRepository ePublicUserRepository) {
        this.ePublicUserRepository = ePublicUserRepository;
    }

    @Override
    public IntegerId findIdByUuId(UniversallyUniqueId uuId) {
        if (uuId.isEmpty()) throw new IllegalArgumentException(uuId.memorySignature());
        EPublicUser ePublicUser = ePublicUserRepository.findByUuId(uuId.memorySignature());
        return ePublicUser == null ? IntegerId.empty() : ePublicUser.getUserId();
    }

    @Override
    public Timestamp isBannedUntil(UniversallyUniqueId uuId) {
        if (uuId.isEmpty()) throw new IllegalArgumentException(uuId.memorySignature());
        EPublicUser ePublicUser = ePublicUserRepository.findByUuId(uuId.memorySignature());
        return ePublicUser == null ? Timestamp.empty() : ePublicUser.getBannedUntil();
    }
}
