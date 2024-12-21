package org.herovole.blogproj.infra.datasource;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.comment.CommentBlackList;
import org.herovole.blogproj.domain.comment.CommentBlackUnit;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.infra.filesystem.LocalFile;

import java.io.IOException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentBlackListLocalFile implements CommentBlackList {

    public static CommentBlackList of(LocalFile blackListFile) throws IOException {
        CommentBlackUnit[] blacklist = blackListFile.readLines().map(CommentBlackUnit::fromLine).toArray(CommentBlackUnit[]::new);
        return new CommentBlackListLocalFile(blacklist);
    }

    private final CommentBlackUnit[] blacklist;

    public CommentBlackUnit detect(CommentText text) {
        for (CommentBlackUnit unit : blacklist) {
            if (unit.appliesTo(text.memorySignature())) return unit;
        }
        return CommentBlackUnit.empty();
    }
}
