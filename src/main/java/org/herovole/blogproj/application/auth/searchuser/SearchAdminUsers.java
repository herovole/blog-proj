package org.herovole.blogproj.application.auth.searchuser;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchAdminUsers {

    private static final Logger logger = LoggerFactory.getLogger(SearchAdminUsers.class.getSimpleName());

    private final AdminUserDatasource adminUserDatasource;
    private final GenericPresenter<SearchAdminUsersOutput> presenter;

    @Autowired
    public SearchAdminUsers(@Qualifier("adminUserDatasource") AdminUserDatasource adminUserDatasource, GenericPresenter<SearchAdminUsersOutput> presenter) {
        this.adminUserDatasource = adminUserDatasource;
        this.presenter = presenter;
    }

    public void process(SearchAdminUsersInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        if (!input.isDetailed()) {
            presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("not supported").interruptProcess();
        }

        //PagingRequest option = input.getPagingRequest();
        AdminUser[] users = adminUserDatasource.search();
        logger.info("job successful.");

        SearchAdminUsersOutput output = SearchAdminUsersOutput.builder()
                .users(users)
                .total(users.length)
                .build();
        this.presenter.setContent(output);
    }
}
