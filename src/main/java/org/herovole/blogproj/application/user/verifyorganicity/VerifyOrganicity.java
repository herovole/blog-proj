package org.herovole.blogproj.application.user.verifyorganicity;

import org.herovole.blogproj.domain.comment.ThirdpartyBotDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyOrganicity {

    private static final Logger logger = LoggerFactory.getLogger(VerifyOrganicity.class.getSimpleName());
    private static final float THRESHOLD = 0.5f;

    private final ThirdpartyBotDetection thirdpartyBotDetection;

    @Autowired
    public VerifyOrganicity(ThirdpartyBotDetection thirdpartyBotDetection) {
        this.thirdpartyBotDetection = thirdpartyBotDetection;
    }

    public VerifyOrganicityOutput process(VerifyOrganicityInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        Float probabilityOfBeingHuman = thirdpartyBotDetection.receiveProbabilityOfBeingHuman(
                input.getVerificationToken(),
                input.getIPv4Address()
        );
        logger.info("User {} score {}", input.getUuId().letterSignature(), probabilityOfBeingHuman);

        return new VerifyOrganicityOutput(probabilityOfBeingHuman == null ? null : THRESHOLD < probabilityOfBeingHuman);
    }
}
