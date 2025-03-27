package org.herovole.blogproj.domain.comment.importedtext;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AdminImportedSourceCommentsTest {

    private static final String test1 = """
            #_1
            $_アメリカ
            __書き込み1
            書き込み12__
                        
            #_2
            $_ドイツ
            __書き込み2
            書き込み22__
                        
            #_3
            $_セルビア
            __書き込み3
            書き込み32__
            """;
    private static final String text = """
            書き込み1
            書き込み12""";

    @Test
    void buildSourceComments() {
        CommentUnits commentUnits = AdminImportedSourceComments.of(test1).buildSourceComments(AdminImportedSourceCommentTest.countryTagDatasource);

        IntegerId i1 = IntegerId.valueOf(1);
        IntegerId i2 = IntegerId.valueOf(2);
        IntegerId i3 = IntegerId.valueOf(3);

        CommentUnit c1 = commentUnits.findByInArticleCommentId(i1);
        CommentUnit c2 = commentUnits.findByInArticleCommentId(i2);
        CommentUnit c3 = commentUnits.findByInArticleCommentId(i3);

        assertFalse(c1.isEmpty());
        assertEquals(i1, c1.getCommentId());
        assertFalse(c2.isEmpty());
        assertEquals(i2, c2.getCommentId());
        assertFalse(c3.isEmpty());
        assertEquals(i3, c3.getCommentId());

    }
}