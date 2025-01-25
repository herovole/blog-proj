package org.herovole.blogproj.application.tag.searchroles;

import lombok.Builder;
import org.herovole.blogproj.domain.adminuser.Role;

import java.util.stream.Stream;

@Builder
public class SearchRolesOutput {
    private final Role[] roles;
    private final long total;

    public Json toJsonModel() {
        return new Json(Stream.of(roles).sorted().map(Role::toJsonModel).toArray(Role.Json[]::new), total);
    }

    public record Json(Role.Json[] tagUnits,
                       long total) {
    }
}
