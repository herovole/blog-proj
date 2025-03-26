package org.herovole.blogproj.domain.comment.importedtext;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminImportedSourceComments {

    public static AdminImportedSourceComments fromFormContent(FormContent formContent) {
        if (formContent == null) return empty();
        FormContent child = formContent.getChildren(API_KEY_IMPORTED_SOURCE_COMMENTS);
        return of(child.getValue());
    }

    public static AdminImportedSourceComments of(String text) {
        return new AdminImportedSourceComments(text);
    }

    public static AdminImportedSourceComments empty() {
        return new AdminImportedSourceComments("");
    }

    private static final String API_KEY_IMPORTED_SOURCE_COMMENTS = "importedSourceComments";

    private final String text;

    public CommentUnits buildSourceComments(CountryTagDatasource countryTagDatasource) {
        final List<CommentUnit> units = new ArrayList<>();
        String[] texts = text.split(AdminImportedSourceComment.BEGIN_COMMENT_NUMBER);
        for (int i = 1; i < texts.length; i++) { //Discard texts[0]
            AdminImportedSourceComment unit = AdminImportedSourceComment.of(texts[i]);
            CommentUnit sourceCommentUnit = unit.buildSourceCommentUnit(countryTagDatasource);
            units.add(sourceCommentUnit);
        }
        return CommentUnits.of(units.toArray(CommentUnit[]::new));
    }
}
