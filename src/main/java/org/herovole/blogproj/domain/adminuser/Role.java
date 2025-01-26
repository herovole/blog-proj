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
    OWNER(10, "OWN", "Owner") {
        @Override
        public boolean editsArticles() {
            return true;
        }

        @Override
        public boolean editsTags() {
            return true;
        }

        @Override
        public boolean editsImages() {
            return true;
        }

        @Override
        public boolean createsUser() {
            return true;
        }

        @Override
        public boolean updatesUserRole() {
            return true;
        }
    },
    ADMIN(20, "ADM", "Administrator") {
        @Override
        public boolean editsArticles() {
            return true;
        }

        @Override
        public boolean editsTags() {
            return true;
        }

        @Override
        public boolean editsImages() {
            return true;
        }
    },
    EDITOR(30, "EDT", "Editor") {
        @Override
        public boolean editsArticles() {
            return true;
        }
    },
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
        if (code == null) return NONE;
        return roles.getOrDefault(code, NONE);
    }

    public static Role fromFormContentRole(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ROLE);
        return of(child.getValue());
    }

    public boolean editsArticles() {
        return false;
    }

    public boolean editsTags() {
        return false;
    }

    public boolean editsImages() {
        return false;
    }

    public boolean createsUser() {
        return false;
    }

    public boolean updatesUserRole() {
        return false;
    }

    public Json toJsonModel() {
        return new Json(this.code, this.label, "");
    }

    public record Json(String id, String tagEnglish, String tagJapanese) {
    }
}
