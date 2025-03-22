package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.publicuser.visit.RealVisit;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "e_article_access")
@Data
public class EArticleAccess implements Serializable {

    public static EArticleAccess fromInsertDomainObj(RealVisit visit) {
        EArticleAccess entity = new EArticleAccess();
        entity.setArticleId(visit.getArticleId().longMemorySignature());
        entity.setPublicUserId(visit.getUserId().longMemorySignature());
        entity.setAton(visit.getIp().aton());
        entity.setAccessTimestamp(visit.getAccessTimestamp().toLocalDateTime());
        return entity;
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "public_user_id")
    private Long publicUserId;

    @Column(name = "aton")
    private Long aton;

    @CreationTimestamp
    @Column(name = "access_timestamp", updatable = false)
    private LocalDateTime accessTimestamp;

}
