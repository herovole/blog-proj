package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerIds;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentText {

    private static final String API_KEY_COMMENT_TEXT = "text";
    private static final String EMPTY = "-";
    private static final String PRETEXT_HIDDEN_COMMENT = "（非公開）";

    private static final int LENGTH_MIN = 1;
    private static final int LENGTH_MAX = 255;
    private static final int LF_COUNT_MAX = 7;
    private static final Pattern REFERRING_FORMAT1 = Pattern.compile("[※米]\\d+");
    private static final Pattern REFERRING_FORMAT2 = Pattern.compile("(>>|＞＞)[\\d０-９]+");

    public static CommentText fromFormContentCommentText(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COMMENT_TEXT);
        return valueOf(child.getValue());
    }

    public static CommentText valueOf(String text) {
        long lfCount = text.chars()
                .filter(c -> c == '\n')
                .count();
        if (LF_COUNT_MAX < lfCount) {
            throw new DomainInstanceGenerationException("Too many line feeds. : " + lfCount + " / " + text);
        }
        if (text.length() < LENGTH_MIN) {
            throw new DomainInstanceGenerationException("Text too short. : " + text.length() + " / " + text);
        }
        if (LENGTH_MAX < text.length()) {
            throw new DomainInstanceGenerationException("Text too long. : " + text.length() + " / " + text);
        }
        return new CommentText(text);
    }

    public static CommentText empty() {
        return new CommentText("");
    }

    public static CommentText hidden() {
        return valueOf(PRETEXT_HIDDEN_COMMENT);
    }

    private final String text;

    public boolean isEmpty() {
        return null == text || text.isEmpty() || EMPTY.equals(text);
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.text;
    }

    public String memorySignature() {
        return this.isEmpty() ? "" : this.text;
    }

    public IntegerIds scanReferringCommentIds() {
        List<String> referredNumbers = new ArrayList<>();
        Matcher matcher1 = REFERRING_FORMAT1.matcher(this.text);
        while (matcher1.find()) {
            String match = matcher1.group();
            referredNumbers.add(match.substring(1));
        }
        Matcher matcher2 = REFERRING_FORMAT2.matcher(this.text);
        while (matcher2.find()) {
            String match = matcher2.group();
            referredNumbers.add(match.substring(2));
        }
        return IntegerIds.of(referredNumbers.toArray(String[]::new));
    }

}
