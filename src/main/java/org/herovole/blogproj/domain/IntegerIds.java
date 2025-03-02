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


    public static IntegerIds fromPostContentTopicTags(FormContent formContent) {
        return fromPostContent(formContent, API_KEY_TOPIC_TAGS);
    }

    public static IntegerIds fromPostContentEditors(FormContent formContent) {
        return fromPostContent(formContent, API_KEY_EDITORS);
    }

    public static IntegerIds fromPostContentReferringCommentIds(FormContent formContent) {
        return fromPostContent(formContent, API_KEY_REFERRING_COMMENT_IDS);
    }

    private static IntegerIds fromPostContent(FormContent formContent, String apiKey) {
        FormContent child = formContent.getChildren(apiKey);
        FormContents arrayChildren = child.getInArray();
        IntegerId[] ids = arrayChildren.stream().map(p -> IntegerId.valueOf(p.getValue())).toArray(IntegerId[]::new);
        return of(ids);
    }

    public static IntegerIds of(int... ids) {
        return of(Arrays.stream(ids).mapToObj(IntegerId::valueOf).toArray(IntegerId[]::new));
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

    public IntegerIds unknownItemsOf(IntegerIds that) {
        return IntegerIds.of(that.stream().filter(e -> !this.has(e)).toArray(IntegerId[]::new));
    }

    public long[] toLongMemorySignature() {
        return stream().filter(e -> !e.isEmpty()).map(IntegerId::longMemorySignature).mapToLong(Long::longValue).toArray();
    }

    public int[] toIntMemorySignature() {
        return stream().filter(e -> !e.isEmpty()).map(IntegerId::intMemorySignature).mapToInt(Integer::intValue).toArray();
    }

    public String[] toStringMemorySignature() {
        return stream().filter(e -> !e.isEmpty()).map(IntegerId::letterSignature).toArray(String[]::new);
    }

    public IntegerId getLargest() {
        return this.isEmpty() ? IntegerId.empty() : this.stream().max(IntegerId::compareTo).orElse(IntegerId.empty());
    }

    @Override
    public Iterator<IntegerId> iterator() {
        return Arrays.stream(this.ids).iterator();
    }

}