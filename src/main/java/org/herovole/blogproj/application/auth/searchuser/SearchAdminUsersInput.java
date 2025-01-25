package org.herovole.blogproj.application.auth.searchuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchAdminUsersInput {

    //?page=...&itemsPerPage=...&isDetailed=...
    public static SearchAdminUsersInput fromFormContent(FormContent formContent) {
        return new SearchAdminUsersInput(
                PagingRequest.fromFormContent(formContent),
                GenericSwitch.fromFormContentIsDetailed(formContent)
        );
    }

    private final PagingRequest pagingRequest;
    private final GenericSwitch isDetailed;

    public boolean isDetailed() {
        return isDetailed.isTrue();
    }
}
