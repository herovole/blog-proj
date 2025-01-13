package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;

@Getter
@Builder
public class InitialAdminRequest {
    private final UserName userName;
    private final Password password;
    private final IPv4Address ip;
}
