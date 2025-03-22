package org.herovole.blogproj.application.image.searchimages;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.image.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SearchImages {

    private static final Logger logger = LoggerFactory.getLogger(SearchImages.class.getSimpleName());

    private final ImageDatasource imageDatasource;
    private final GenericPresenter<SearchImagesOutput> presenter;

    @Autowired
    public SearchImages(
            ImageDatasource imageDatasource,
            GenericPresenter<SearchImagesOutput> presenter
    ) {
        this.imageDatasource = imageDatasource;
        this.presenter = presenter;
    }

    public void process(SearchImagesInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        PagingRequest request = input.getPagingRequest();
        try {
            Images images = imageDatasource.searchSortedByTimestampDesc(request);
            int total = imageDatasource.getTotal();
            SearchImagesOutput output = new SearchImagesOutput(images, total);
            this.presenter.setContent(output);
        } catch (IOException e) {
            logger.error("error - image searching", e);
            presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR).interruptProcess();
        }
    }
}
