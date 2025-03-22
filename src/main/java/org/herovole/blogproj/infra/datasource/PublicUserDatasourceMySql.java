package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;
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
    public IntegerPublicUserId findIdByUuId(UniversallyUniqueId uuId) {
        if (uuId.isEmpty()) throw new IllegalArgumentException(uuId.memorySignature());
        EPublicUser ePublicUser = ePublicUserRepository.findByUuId(uuId.memorySignature());
        return ePublicUser == null ? IntegerPublicUserId.empty() : ePublicUser.getUserId();
    }

    @Override
    public boolean exists(IntegerPublicUserId userId) {
        if (userId.isEmpty()) throw new IllegalArgumentException(userId.letterSignature());
        EPublicUser ePublicUser = ePublicUserRepository.findByUserId(userId.longMemorySignature());
        return ePublicUser != null;
    }

    @Override
    public Timestamp isBannedUntil(IntegerPublicUserId userId) {
        if (userId.isEmpty()) throw new IllegalArgumentException(userId.letterSignature());
        EPublicUser ePublicUser = ePublicUserRepository.findByUserId(userId.longMemorySignature());
        return ePublicUser == null ? Timestamp.empty() : ePublicUser.getBannedUntil();
    }
}
