package org.herovole.blogproj.application.user.searchcomments;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentsSearchOption;
import org.herovole.blogproj.domain.publicuser.PublicIpDatasource;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchUserComments {

    private static final Logger logger = LoggerFactory.getLogger(SearchUserComments.class.getSimpleName());

    private final UserCommentDatasource userCommentDatasource;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicIpDatasource publicIpDatasource;

    private final GenericPresenter<SearchUserCommentsOutput> presenter;

    @Autowired
    public SearchUserComments(
            @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
            PublicUserDatasource publicUserDatasource,
            PublicIpDatasource publicIpDatasource,
            GenericPresenter<SearchUserCommentsOutput> presenter) {
        this.userCommentDatasource = userCommentDatasource;
        this.publicUserDatasource = publicUserDatasource;
        this.publicIpDatasource = publicIpDatasource;
        this.presenter = presenter;
    }

    public void process(SearchUserCommentsInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        if (!input.getIsDetailed().isTrue()) {
            presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR).interruptProcess();
        }
        UserCommentsSearchOption option = input.getSearchOption();
        long count = userCommentDatasource.countComments(option);
        CommentUnits units = userCommentDatasource.searchComments(option);
        SearchUserCommentsOutput output = SearchUserCommentsOutput.builder()
                .commentUnits(units)
                .total(count)
                .build();
        this.presenter.setContent(output);
        logger.info("job successful.");
    }
}
