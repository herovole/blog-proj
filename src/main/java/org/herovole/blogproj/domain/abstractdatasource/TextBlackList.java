package org.herovole.blogproj.domain.abstractdatasource;

import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.HandleName;

public interface TextBlackList {
    TextBlackUnit detectHumanThreat(CommentText text);

    TextBlackUnit detectHumanThreat(HandleName text);

    TextBlackUnit detectSystemThreat(String parameter);
}
