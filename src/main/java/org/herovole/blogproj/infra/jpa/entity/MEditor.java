package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
    CREATE TABLE m_editor (
      id INT PRIMARY KEY,
      name VARCHAR(63) NOT NULL,
      update_timestamp timestamp default current_timestamp on update current_timestamp,
      insert_timestamp timestamp default current_timestamp
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "m_editor")
@Data
public class MEditor implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

}
