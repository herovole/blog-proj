package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "e_public_user")
@Data
public class EPublicUser implements Serializable {

    public static EPublicUser fromUuId(UniversallyUniqueId uuId) {
        if (uuId.isEmpty()) throw new IllegalArgumentException();
        EPublicUser entity = new EPublicUser();
        entity.setUuId(uuId.memorySignature());
        return entity;
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "uu_id", columnDefinition = "CHAR")
    private String uuId;

    @Column(name = "banned_until")
    private LocalDateTime bannedUntil;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    public IntegerId getUserId() {
        return IntegerId.valueOf(id);
    }

    public Timestamp getBannedUntil() {
        return Timestamp.valueOf(bannedUntil);
    }
}
