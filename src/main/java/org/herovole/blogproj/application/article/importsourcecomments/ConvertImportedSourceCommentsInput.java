package org.herovole.blogproj.application.article.importsourcecomments;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.comment.importedtext.AdminImportedSourceComments;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertImportedSourceCommentsInput {

    public static ConvertImportedSourceCommentsInput fromPostContent(FormContent formContent) {
        return new ConvertImportedSourceCommentsInput(AdminImportedSourceComments.fromFormContent(formContent));
    }

    private final AdminImportedSourceComments adminImportedSourceComments;

}
