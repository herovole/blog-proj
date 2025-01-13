package org.herovole.blogproj.infra.datasource;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.infra.filesystem.LocalFile;

import java.io.IOException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TextBlackListLocalFile implements TextBlackList {

    public static TextBlackList of(LocalFile blackListFile) throws IOException {
        TextBlackUnit[] blacklist = blackListFile.readLines().map(TextBlackUnit::fromLine).toArray(TextBlackUnit[]::new);
        return new TextBlackListLocalFile(blacklist);
    }

    private final TextBlackUnit[] blacklist;

    private TextBlackUnit detect(String text) {
        for (TextBlackUnit unit : blacklist) {
            if (unit.appliesTo(text)) return unit;
        }
        return TextBlackUnit.empty();
    }

    public TextBlackUnit detect(CommentText text) {
        return this.detect(text.memorySignature());
    }

    public TextBlackUnit detect(HandleName text) {
        return this.detect(text.memorySignature());
    }
}
