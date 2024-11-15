package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCountryRepository extends JpaRepository<MCountry, Long> {

    @Query(value = "Select * from m_country limit 1", nativeQuery = true)
    MCountry findOne();

    @Query(value = "Select * from m_country order by iso_2",
            nativeQuery = true)
    List<MCountry> findAll();

    @Query(value = "Select * from m_country Where name_en like Concat(:name, '%') ",
            nativeQuery = true)
    List<MCountry> findForwardMatchCandidates(@Param("name") String name);

}
