package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EPublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EPublicUserRepository extends JpaRepository<EPublicUser, Long> {

    @Query(value = "Select * from e_public_user limit 1", nativeQuery = true)
    EPublicUser findOne();

}
