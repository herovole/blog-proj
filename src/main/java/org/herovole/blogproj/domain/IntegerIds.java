package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerIds {

    private static final String EMPTY = "-";
    private static final String SEP = ",";
    private static final String API_KEY_EDITORS = "editors";
    private static final String API_KEY_REFERRING_COMMENT_IDS = "referringCommentIds";

    public static IntegerIds fromPostContentEditors(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_EDITORS);
        PostContents arrayChildren = child.getInArray();
        IntegerId[] ids = arrayChildren.stream().map(p -> IntegerId.valueOf(p.getValue())).toArray(IntegerId[]::new);
        return of(ids);
    }

    public static IntegerIds fromPostContentReferringCommentIds(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_REFERRING_COMMENT_IDS);
        PostContents arrayChildren = child.getInArray();
        IntegerId[] ids = arrayChildren.stream().map(p -> IntegerId.valueOf(p.getValue())).toArray(IntegerId[]::new);
        return of(ids);
    }

    public static IntegerIds of(long[] ids) {
        IntegerId[] fieldIds = Arrays.stream(ids)
                .mapToObj(IntegerId::valueOf)
                .toArray(IntegerId[]::new);
        return of(fieldIds);
    }

    public static IntegerIds of(String[] ids) {
        long[] fieldIds = Arrays.stream(ids).mapToLong(Long::parseLong).toArray();
        return of(fieldIds);
    }

    public static IntegerIds of(IntegerId[] ids) {
        return new IntegerIds(ids);
    }

    public static IntegerIds empty() {
        return new IntegerIds(new IntegerId[]{});
    }

    private final IntegerId[] ids;

    public boolean isEmpty() {
        return this.ids.length == 0;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.join(SEP, Arrays.stream(ids).map(String::valueOf).toArray(String[]::new));
    }

}