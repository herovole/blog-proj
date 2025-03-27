package org.herovole.blogproj.domain.comment.importedtext;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.RealSourceCommentUnit;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminImportedSourceComment {
    public static AdminImportedSourceComment of(String text) {
        return new AdminImportedSourceComment(text);
    }

    static final String BEGIN_COMMENT_ID = "#_";
    private static final String BEGIN_COUNTRY = "\\$_";
    private static final String BEGIN_COMMENT_TEXT = "__";
    private static final String END_COMMENT_TEXT = "__";
    private static final String LF1 = "\n";
    private static final String LF2 = "\r";
    private static final Pattern PATTERN_COMMENT_ID = Pattern.compile(BEGIN_COMMENT_ID + "(?s)(.*?)" + BEGIN_COUNTRY);
    private static final Pattern PATTERN_COUNTRY_CODE = Pattern.compile(BEGIN_COUNTRY + "(?s)(.*?)" + BEGIN_COMMENT_TEXT);
    private static final Pattern PATTERN_COMMENT_TEXT = Pattern.compile(BEGIN_COMMENT_TEXT + "(?s)(.*?)" + END_COMMENT_TEXT);

    private final String text;

    private IntegerId extractCommentId() {
        Matcher matcher = PATTERN_COMMENT_ID.matcher(text);
        if (matcher.find()) {
            return IntegerId.valueOf(matcher.group(1).replace(LF1, "").replace(LF2, ""));
        }
        return IntegerId.empty();
    }

    private CountryCode extractCountryCode(CountryTagDatasource countryTagDatasource) {
        Matcher matcher = PATTERN_COUNTRY_CODE.matcher(text);
        if (matcher.find()) {
            String partOfCountryName = matcher.group(1).replace(LF1, "").replace(LF2, "");
            CountryCodes codes = countryTagDatasource.searchCandidatesByName(partOfCountryName);
            return codes.getOne();
        }
        return CountryCode.empty();
    }

    private CommentText extractCommentText() {
        Matcher matcher = PATTERN_COMMENT_TEXT.matcher(text);
        if (matcher.find()) {
            return CommentText.valueOf(matcher.group(1).replace(LF2, ""));
        }
        return CommentText.empty();
    }

    public RealSourceCommentUnit buildSourceCommentUnit(CountryTagDatasource countryTagDatasource) {
        CommentText commentText = this.extractCommentText();
        return RealSourceCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(this.extractCommentId())
                .country(this.extractCountryCode(countryTagDatasource))
                .commentText(commentText)
                .isHidden(GenericSwitch.negative())
                .referringCommentIds(commentText.scanReferringCommentIds())
                .build();
    }
}
