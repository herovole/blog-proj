package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerIds implements Iterable<IntegerId> {

    private static final String EMPTY = "-";
    private static final String SEP = ",";
    private static final String API_KEY_EDITORS = "editors";
    private static final String API_KEY_REFERRING_COMMENT_IDS = "referringCommentIds";
    private static final String API_KEY_TOPIC_TAGS = "topicTags";


    public static IntegerIds fromPostContentTopicTags(PostContent postContent) {
        return fromPostContent(postContent, API_KEY_TOPIC_TAGS);
    }

    public static IntegerIds fromPostContentEditors(PostContent postContent) {
        return fromPostContent(postContent, API_KEY_EDITORS);
    }

    public static IntegerIds fromPostContentReferringCommentIds(PostContent postContent) {
        return fromPostContent(postContent, API_KEY_REFERRING_COMMENT_IDS);
    }

    private static IntegerIds fromPostContent(PostContent postContent, String apiKey) {
        PostContent child = postContent.getChildren(apiKey);
        PostContents arrayChildren = child.getInArray();
        IntegerId[] ids = arrayChildren.stream().map(p -> IntegerId.valueOf(p.getValue())).toArray(IntegerId[]::new);
        return of(ids);
    }

    public static IntegerIds of(String commaSeparatedIds) {
        if (commaSeparatedIds == null || commaSeparatedIds.isEmpty()) return empty();
        return of(commaSeparatedIds.split(SEP));
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

    public Stream<IntegerId> stream() {
        return Arrays.stream(ids);
    }

    public boolean isEmpty() {
        return this.ids.length == 0;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.join(SEP, Arrays.stream(ids).map(String::valueOf).toArray(String[]::new));
    }

    public String commaSeparatedMemorySignature() {
        return this.isEmpty() ? null : String.join(SEP, Arrays.stream(ids).map(IntegerId::letterSignature).toArray(String[]::new));
    }

    public boolean has(IntegerId integerId) {
        return Arrays.asList(this.ids).contains(integerId);
    }

    public IntegerIds lack(IntegerIds that) {
        return IntegerIds.of(that.stream().filter(e -> !this.has(e)).toArray(IntegerId[]::new));
    }

    public long[] toLongMemorySignature() {
        return stream().filter(e -> !e.isEmpty()).map(IntegerId::longMemorySignature).mapToLong(Long::longValue).toArray();
    }

    public int[] toIntMemorySignature() {
        return stream().filter(e -> !e.isEmpty()).map(IntegerId::intMemorySignature).mapToInt(Integer::intValue).toArray();
    }

    @Override
    public Iterator<IntegerId> iterator() {
        return Arrays.stream(this.ids).iterator();
    }
}