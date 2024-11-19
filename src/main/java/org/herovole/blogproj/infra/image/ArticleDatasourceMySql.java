package org.herovole.blogproj.infra.image;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleEditingPage;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.CountryCode;
import org.herovole.blogproj.domain.tag.CountryCodes;
import org.herovole.blogproj.infra.jpa.entity.AArticle;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasCountry;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasEditor;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasTopicTag;
import org.herovole.blogproj.infra.jpa.entity.ASourceComment;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasCountryRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasEditorRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasTopicTagRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleRepository;
import org.herovole.blogproj.infra.jpa.repository.ASourceCommentRepository;

import java.util.List;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDatasourceMySql implements ArticleDatasource {

    private final AArticleRepository aArticleRepository;
    private final AArticleHasTopicTagRepository aArticleHasTopicTagRepository;
    private final AArticleHasCountryRepository aArticleHasCountryRepository;
    private final AArticleHasEditorRepository aArticleHasEditorRepository;
    private final ASourceCommentRepository aSourceCommentRepository;

    public ArticleEditingPage findById(IntegerId articleId) {
        AArticle jpaArticle = aArticleRepository.findById(articleId.memorySignature()).orElse(null);
        if (jpaArticle == null) return ArticleEditingPage.empty();
        ArticleEditingPage article = jpaArticle.toDomainObj();

        List<AArticleHasTopicTag> jpaTopicTags = aArticleHasTopicTagRepository.findByArticleId(articleId.memorySignature());
        IntegerIds topicTags = IntegerIds.of(jpaTopicTags.stream().map(AArticleHasTopicTag::toTopicTagId).toArray(IntegerId[]::new));

        List<AArticleHasCountry> jpaCountryCodes = aArticleHasCountryRepository.findByArticleId(articleId.memorySignature());
        CountryCodes coutryCodes = CountryCodes.of(jpaCountryCodes.stream().map(AArticleHasCountry::toIso2).toArray(CountryCode[]::new));

        List<AArticleHasEditor> jpaEditors = aArticleHasEditorRepository.findByArticleId(articleId.memorySignature());
        IntegerIds editors = IntegerIds.of(jpaEditors.stream().map(AArticleHasEditor::toEditorId).toArray(IntegerId[]::new));

        List<ASourceComment> jpaSourceComments = aSourceCommentRepository.findByArticleId(articleId.memorySignature());
        CommentUnits sourceComments = CommentUnits.of(jpaSourceComments.stream().map(ASourceComment::toDomainObj).toArray(CommentUnit[]::new));

        return article.append(topicTags, coutryCodes, editors, sourceComments);
    }
}
