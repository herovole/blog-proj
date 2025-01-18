package org.herovole.blogproj.application.error;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UseCaseErrorType {
    NONE("NNE") {
        @Override
        public void throwException(String message) {
            // No action required
        }
    },
    SERVER_ERROR("SVR") {
        @Override
        public void throwException(String message) throws GenericSeverErrorException {
            throw new GenericSeverErrorException(message);
        }
    },
    GENERIC_USER_ERROR("GUE") {
        @Override
        public void throwException(String message) throws GenericUserErrorException {
            throw new GenericUserErrorException(message);
        }
    },
    HUMAN_THREATENING_PHRASE("HTH") {
        @Override
        public void throwException(String message) throws HumanThreateningPhraseException {
            throw new HumanThreateningPhraseException(message);
        }
    },
    SYSTEM_THREATENING_PHRASE("STH") {
        @Override
        public void throwException(String message) throws SystemThreateningPhraseException {
            throw new SystemThreateningPhraseException(message);
        }
    },
    BOT("BOT") {
        @Override
        public void throwException(String message) throws BotDetectionException {
            throw new BotDetectionException(message);
        }

    },
    BAN("BAN") {
        @Override
        public void throwException(String message) throws SuspendedUserException {
            throw new SuspendedUserException(message);
        }
    },
    AUTH_FAILURE("ATH") {
        @Override
        public void throwException(String message) throws AuthenticationFailureException {
            throw new AuthenticationFailureException(message);
        }
    };
    private final String code;

    public String memorySignature() {
        return code;
    }

    public abstract void throwException(String message) throws ApplicationProcessException;
}
