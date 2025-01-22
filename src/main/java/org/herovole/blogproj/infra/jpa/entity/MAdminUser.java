package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.RealAdminUser;
import org.herovole.blogproj.domain.adminuser.Role;
import org.herovole.blogproj.domain.adminuser.UserName;
import org.herovole.blogproj.domain.time.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "m_admin_user")
@Data
public class MAdminUser implements Serializable {

    public static MAdminUser fromDomainObj(AdminUser domainObj) {
        if (!(domainObj instanceof RealAdminUser domainObj1)) throw new IllegalArgumentException();
        MAdminUser entity = new MAdminUser();
        entity.setName(domainObj1.getUserName().memorySignature());
        entity.setRole(domainObj1.getRole().getCode());
        entity.setCredentialEncode(domainObj1.getCredentialEncode());
        entity.setAccessTokenAton(domainObj1.getAccessTokenIp().memorySignature());
        entity.setAccessToken(domainObj1.getAccessToken().memorySignature());
        entity.setAccessTokenExpiry(domainObj1.getAccessTokenExpiry().toLocalDateTime());
        return entity;
    }

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "role", columnDefinition = "CHAR")
    private String role;
    @Column(name = "credential_encode")
    private String credentialEncode;

    @Column(name = "access_token_aton")
    private Long accessTokenAton;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expiry")
    private LocalDateTime accessTokenExpiry;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    public AdminUser toDomainObj() {
        return RealAdminUser.builder()
                .userName(UserName.valueOf(name))
                .role(Role.valueOf(role))
                .credentialEncode(credentialEncode)
                .accessToken(AccessToken.valueOf(accessToken))
                .accessTokenIp(IPv4Address.valueOf(accessTokenAton))
                .accessTokenExpiry(Timestamp.valueOf(accessTokenExpiry))
                .build();
    }

}
