package org.herovole.blogproj.application.article.importsourcecomments;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
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
        CommentUnits commentUnits = text.buildSourceComments(countryTagDatasource);
        presenter.setContent(commentUnits);

        logger.info("job successful.");

    }
}
