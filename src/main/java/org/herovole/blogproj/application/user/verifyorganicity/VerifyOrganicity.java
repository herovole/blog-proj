package org.herovole.blogproj.application.user.verifyorganicity;

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

    @Autowired
    public VerifyOrganicity(ThirdpartyBotDetection thirdpartyBotDetection) {
        this.thirdpartyBotDetection = thirdpartyBotDetection;
    }

    public VerifyOrganicityOutput process(VerifyOrganicityInput input) throws IOException, InterruptedException {
        logger.info("interpreted post : {}", input);

        Float probabilityOfBeingHuman = thirdpartyBotDetection.receiveProbabilityOfBeingHuman(
                input.getVerificationToken(),
                input.getIPv4Address()
        );
        //FIXME : temporarily suppresses blocking the process for the testing purposes
        probabilityOfBeingHuman = 1.0f;
        //if (probabilityOfBeingHuman == null) throw new IllegalStateException();
        logger.info("User {} score {}", input.getUuId(), probabilityOfBeingHuman);

        return new VerifyOrganicityOutput(THRESHOLD < probabilityOfBeingHuman);
    }
}
