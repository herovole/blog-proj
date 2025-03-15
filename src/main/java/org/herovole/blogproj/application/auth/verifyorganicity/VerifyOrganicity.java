package org.herovole.blogproj.application.auth.verifyorganicity;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.comment.ThirdpartyBotDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifyOrganicity {

    private static final Logger logger = LoggerFactory.getLogger(VerifyOrganicity.class.getSimpleName());
    private static final float THRESHOLD = 0.5f;

    private final ThirdpartyBotDetection thirdpartyBotDetection;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public VerifyOrganicity(ThirdpartyBotDetection thirdpartyBotDetection, GenericPresenter<Object> presenter) {
        this.thirdpartyBotDetection = thirdpartyBotDetection;
        this.presenter = presenter;
    }

    public void process(VerifyOrganicityInput input) throws IOException, InterruptedException, ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        Float probabilityOfBeingHuman = thirdpartyBotDetection.receiveProbabilityOfBeingHuman(
                input.getVerificationToken(),
                input.getIPv4Address()
        );
        if (probabilityOfBeingHuman == null) throw new IllegalStateException();
        logger.info("User {} score {}", input.getUserId(), probabilityOfBeingHuman);

        if(probabilityOfBeingHuman < THRESHOLD) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.BOT).interruptProcess();
        }

    }
}
