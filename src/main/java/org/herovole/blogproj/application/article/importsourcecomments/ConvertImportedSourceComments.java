package org.herovole.blogproj.application.article.importsourcecomments;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.comment.importedtext.AdminImportedSourceComments;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertImportedSourceComments {

    private static final Logger logger = LoggerFactory.getLogger(ConvertImportedSourceComments.class.getSimpleName());

    private final CountryTagDatasource countryTagDatasource;
    private final GenericPresenter<CommentUnits> presenter;

    @Autowired
    public ConvertImportedSourceComments(CountryTagDatasource countryTagDatasource,
                                         GenericPresenter<CommentUnits> presenter) {
        this.countryTagDatasource = countryTagDatasource;
        this.presenter = presenter;
    }

    public void process(ConvertImportedSourceCommentsInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        AdminImportedSourceComments text = input.getAdminImportedSourceComments();
        if(text.isEmpty()) {
            logger.error("no data: {}", input);
            presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR).interruptProcess();
        }

        CommentUnits commentUnits = text.buildSourceComments(countryTagDatasource);
        CommentUnits sortedCommentUnits = commentUnits.sort();
        presenter.setContent(sortedCommentUnits);

        logger.info("job successful.");

    }
}
