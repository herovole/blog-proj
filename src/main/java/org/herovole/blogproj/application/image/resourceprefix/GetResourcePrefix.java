package org.herovole.blogproj.application.image.resourceprefix;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetResourcePrefix {

    private static final Logger logger = LoggerFactory.getLogger(GetResourcePrefix.class.getSimpleName());

    private final ImageDatasource imageDatasource;
    private final GenericPresenter<GetResourcePrefixOutput> presenter;

    @Autowired
    public GetResourcePrefix(
            ImageDatasource imageDatasource,
            GenericPresenter<GetResourcePrefixOutput> presenter
    ) {
        this.imageDatasource = imageDatasource;
        this.presenter = presenter;
    }

    public void process() throws ApplicationProcessException {
        String prefix = imageDatasource.imageResourcePrefix();
        GetResourcePrefixOutput output = new GetResourcePrefixOutput(prefix);
        this.presenter.setContent(output);
    }
}
