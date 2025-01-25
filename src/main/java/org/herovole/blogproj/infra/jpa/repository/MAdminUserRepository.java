package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.MAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MAdminUserRepository extends JpaRepository<MAdminUser, Long> {

    @Query(value = "Select * from m_admin_user limit 1", nativeQuery = true)
    MAdminUser findOne();

    @Query(value = "Select * from m_admin_user " +
            "  Where " +
            "    name = :name " +
            "  ", nativeQuery = true)
    List<MAdminUser> findByUserName(@Param("name") String name);

    @Query(value = "Select * from m_admin_user " +
            "  Where " +
            "    access_token = :accessToken ",
            nativeQuery = true)
    List<MAdminUser> findByAccessToken(@Param("accessToken") String accessToken);

    @Query(value = "Select * from m_admin_user " +
            " Order By id " +
            "", nativeQuery = true)
    List<MAdminUser> search();
}
