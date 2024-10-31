package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/*
CREATE TABLE m_country (
  id INT PRIMARY KEY,
  name_en VARCHAR(63) NOT NULL,
  name_ja VARCHAR(31) NOT NULL,
  iso_2 char(2) NOT NULL,
  iso_3 char(3) NOT NULL,
  icon_base VARCHAR(63) NOT NULL,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 */

@Entity
@Table(name = "m_country")
@Data
public class MCountry implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_ja")
    private String nameJa;

    @Column(name = "iso_2")
    private String iso2;

    @Column(name = "iso_3")
    private String iso3;

    @Column(name = "icon_base")
    private String iconBase;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCountry mCountry = (MCountry) o;
        return nameEn.equals(mCountry.nameEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEn);
    }
}
