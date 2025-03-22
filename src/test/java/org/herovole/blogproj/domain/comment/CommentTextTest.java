package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.domain.IntegerIds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTextTest {

    @Test
    void scanReferringCommentIds() {
        CommentText i0 = CommentText.valueOf("こんにちは");
        CommentText i1 = CommentText.valueOf("napwoeivjf>>1234nvweoa5678nfaovweij>>9012");
        CommentText i2 = CommentText.valueOf("napwoeivjf※1234nvweoa5678nfaovweij※9012");
        CommentText i3 = CommentText.valueOf("napwoeivjf米1234nvweoa5678nfaovweij米9012");
        CommentText i4 = CommentText.valueOf("napwoeivjf＞＞１２３４nvweoa>5678nfaovweij＞＞90１２");

        IntegerIds expected = IntegerIds.of(1234, 9012);

        assertEquals(IntegerIds.empty(), i0.scanReferringCommentIds());
        assertEquals(expected, i1.scanReferringCommentIds());
        assertEquals(expected, i2.scanReferringCommentIds());
        assertEquals(expected, i3.scanReferringCommentIds());
        assertEquals(expected, i4.scanReferringCommentIds());
    }
}