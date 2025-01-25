package org.herovole.blogproj.application.auth.searchuser;

import lombok.Builder;
import org.herovole.blogproj.domain.adminuser.AdminUser;

import java.util.stream.Stream;

@Builder
public class SearchAdminUsersOutput {
    private final AdminUser[] users;
    private final long total;

    public Json toJsonModel() {
        return new Json(Stream.of(users).map(AdminUser::toJsonModel).toArray(AdminUser.Json[]::new), total);
    }

    public record Json(AdminUser.Json[] users,
                       long total) {
    }
}
