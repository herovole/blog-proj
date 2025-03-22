package org.herovole.blogproj.application.user.visit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.visit.RealVisit;
import org.herovole.blogproj.domain.publicuser.visit.Visit;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitArticleInput {
    private final IntegerId articleId;
    private final IntegerPublicUserId userId;
    private final IPv4Address iPv4Address;

    Visit buildVisit() {
        return RealVisit.builder()
                .id(IntegerId.empty())
                .articleId(articleId)
                .userId(userId)
                .ip(iPv4Address)
                .accessTimestamp(Timestamp.now())
                .build();
    }
}

