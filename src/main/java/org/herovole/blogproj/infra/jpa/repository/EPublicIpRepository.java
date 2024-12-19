package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EPublicIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EPublicIpRepository extends JpaRepository<EPublicIp, Long> {

    @Query(value = "Select * from e_public_ip limit 1", nativeQuery = true)
    EPublicIp findOne();

    @Query(value = "Select * from e_public_ip Where aton = :aton", nativeQuery = true)
    EPublicIp findByAton(@Param("aton") long aton);
}
