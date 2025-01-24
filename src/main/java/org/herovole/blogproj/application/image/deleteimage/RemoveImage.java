package org.herovole.blogproj.application.image.deleteimage;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.accesskey.AccessKeyAsPath;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.image.ImageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RemoveImage {

    private static final Logger logger = LoggerFactory.getLogger(RemoveImage.class.getSimpleName());

    private final ImageDatasource imageDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public RemoveImage(
            ImageDatasource imageDatasource,
            GenericPresenter<Object> presenter
    ) {
        this.imageDatasource = imageDatasource;
        this.presenter = presenter;
    }

    public void process(RemoveImageInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        ImageName imageName = input.getImageName();
        AccessKey fileName = AccessKeyAsPath.valueOf(imageName);

        try {
            Image existingImage = imageDatasource.findByName(fileName);
            if (existingImage.isEmpty()) {
                presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                        .setMessage("There's no requested file to delete.").interruptProcess();
            }
            imageDatasource.remove(fileName);
        } catch (IOException e) {
            logger.error("error - image removing", e);
            presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR).interruptProcess();
        }
    }
}
