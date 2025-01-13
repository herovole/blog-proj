package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;

@Getter
@Builder
public class ContinualAdminRequest {
    private final AccessToken accessToken;
    private final IPv4Address accessTokenIp;
}
