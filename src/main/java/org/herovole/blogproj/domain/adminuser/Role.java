package org.herovole.blogproj.domain.adminuser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.FormContent;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Role {


    NONE(0, "NON", "None"),
    OWNER(10, "OWN", "Owner"),
    ADMIN(20, "ADM", "Administrator"),
    EDITOR(30, "EDT", "Editor"),
    SPECTATOR(100, "SPC", "Spectator");

    private final int order;
    private final String code;
    private final String label;

    private static final String API_KEY_ROLE = "role";
    private static final Map<String, Role> roles = new HashMap<>();

    static {
        for (Role role : values()) {
            roles.put(role.code, role);
        }
    }

    public static Role of(String code) {
        return roles.getOrDefault(code, NONE);
    }

    public static Role fromFormContentRole(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ROLE);
        return valueOf(child.getValue());
    }
}
