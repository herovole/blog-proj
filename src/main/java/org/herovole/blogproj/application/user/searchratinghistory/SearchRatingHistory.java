package org.herovole.blogproj.application.user.searchratinghistory;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.rating.RatingLogs;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchRatingHistory {

    private static final Logger logger = LoggerFactory.getLogger(SearchRatingHistory.class.getSimpleName());

    private final UserCommentDatasource userCommentDatasource;
    private final GenericPresenter<RatingLogs> presenter;

    @Autowired
    public SearchRatingHistory(
            @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
            GenericPresenter<RatingLogs> presenter) {
        this.userCommentDatasource = userCommentDatasource;
        this.presenter = presenter;
    }

    public void process(SearchRatingHistoryInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        IPv4Address ip = input.getIPv4Address();
        IntegerPublicUserId userId = input.getUserId();
        IntegerId articleId = input.getArticleId();

        RatingLogs pastRatingLogsByUserId = this.userCommentDatasource.findActiveRatingHistoryOfArticle(articleId, userId);
        RatingLogs pastRatingLogsByIp = this.userCommentDatasource.findActiveRatingHistoryOfArticle(articleId, ip, Date.today());

        RatingLogs combinedRatingLogs = pastRatingLogsByUserId.combine(pastRatingLogsByIp);

        this.presenter.setContent(combinedRatingLogs);
        logger.info("job successful.");
    }
}
