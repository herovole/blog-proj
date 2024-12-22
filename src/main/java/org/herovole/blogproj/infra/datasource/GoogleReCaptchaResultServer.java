package org.herovole.blogproj.infra.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.comment.ThirdpartyBotDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleReCaptchaResultServer implements ThirdpartyBotDetection {

    public static GoogleReCaptchaResultServer of(String secretKey) {
        return new GoogleReCaptchaResultServer(secretKey);
    }

    private static final Logger logger = LoggerFactory.getLogger(GoogleReCaptchaResultServer.class);
    private static final URI ENDPOINT = URI.create("https://www.google.com/recaptcha/api/siteverify");
    private final String secretKey;

    @Override
    public Float receiveProbabilityOfBeingHuman(String verificationToken, IPv4Address iPv4Address) throws IOException, InterruptedException {

        RequestPayloadModel requestPayloadModel = new RequestPayloadModel(
                secretKey,
                verificationToken,
                iPv4Address.toRegularFormat()
        );

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(ENDPOINT)
                    .header("Content-Type", "application/json")
                    .POST(requestPayloadModel.toPostRequestPayload())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> responseRaw = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + responseRaw.statusCode());
        System.out.println("Response Body: " + responseRaw.body());


        ObjectMapper objectMapper = new ObjectMapper();
        ResponsePayloadModel responseInterpreted = objectMapper.readValue(responseRaw.body(), ResponsePayloadModel.class);

        logger.info("Request : {} / Response : {}", requestPayloadModel, responseInterpreted);
        if (responseInterpreted.success) return responseInterpreted.score;
        return null;
    }

    @ToString
    private record RequestPayloadModel(
            String secret,
            String response,
            String remoteip
    ) {
        HttpRequest.BodyPublisher toPostRequestPayload() throws JsonProcessingException {
            return HttpRequest.BodyPublishers.ofString(
                    new ObjectMapper().writeValueAsString(this)
            );
        }
    }

    @ToString
    private record ResponsePayloadModel(
            boolean success,
            float score,
            String action,
            LocalDateTime challenge_ts,
            String hostname,
            String[] errorCodes
    ) {
    }
}
