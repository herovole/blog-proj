package org.herovole.blogproj.infra.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.infra.filesystem.LocalFile;

import java.io.IOException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TextBlackListLocalFile implements TextBlackList {

    public static class Builder {
        private LocalFile systemBlackListFile;
        private LocalFile humanBlackListFile;

        public Builder setSystemBlackListFile(LocalFile systemBlackListFile) {
            this.systemBlackListFile = systemBlackListFile;
            return this;
        }

        public Builder setHumanBlackListFile(LocalFile humanBlackListFile) {
            this.humanBlackListFile = humanBlackListFile;
            return this;
        }

        public TextBlackListLocalFile build() throws IOException {
            TextBlackUnit[] systemBlacklist = systemBlackListFile.readLines().map(TextBlackUnit::fromLine).toArray(TextBlackUnit[]::new);
            TextBlackUnit[] humanBlacklist = humanBlackListFile.readLines().map(TextBlackUnit::fromLine).toArray(TextBlackUnit[]::new);
            return new TextBlackListLocalFile(systemBlacklist, humanBlacklist);
        }
    }

    private final TextBlackUnit[] systemBlacklist;
    private final TextBlackUnit[] humanBlacklist;

    public TextBlackUnit detectSystemThreat(String text) {
        for (TextBlackUnit unit : systemBlacklist) {
            if (unit.appliesTo(text)) return unit;
        }
        return TextBlackUnit.empty();
    }

    private TextBlackUnit detectHumanThreat(String text) {
        for (TextBlackUnit unit : humanBlacklist) {
            if (unit.appliesTo(text)) return unit;
        }
        return TextBlackUnit.empty();
    }

    public TextBlackUnit detectHumanThreat(CommentText text) {
        return this.detectHumanThreat(text.memorySignature());
    }

    public TextBlackUnit detectHumanThreat(HandleName text) {
        return this.detectHumanThreat(text.memorySignature());
    }

}
