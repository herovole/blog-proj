package org.herovole.blogproj.application.image.postimage;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PostImage {

    private static final Logger logger = LoggerFactory.getLogger(PostImage.class.getSimpleName());

    private final ImageDatasource imageDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public PostImage(
            ImageDatasource imageDatasource,
            GenericPresenter<Object> presenter
    ) {
        this.imageDatasource = imageDatasource;
        this.presenter = presenter;
    }

    public void process(PostImageInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        Image image = input.getImage();
        AccessKey fileName = AccessKeyAsPath.nameOf(input.getImage());
        try {
            imageDatasource.persist(fileName, image);
        } catch (IOException e) {
            logger.error("error - image persisting", e);
            presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR).interruptProcess();
        }
    }
}
