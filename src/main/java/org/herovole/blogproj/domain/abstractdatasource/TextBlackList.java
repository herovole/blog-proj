package org.herovole.blogproj.domain.abstractdatasource;

import org.herovole.blogproj.domain.adminuser.UserName;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.HandleName;

public interface TextBlackList {
    TextBlackUnit detect(CommentText text);

    TextBlackUnit detect(HandleName text);

    TextBlackUnit detect(UserName userName);
}
