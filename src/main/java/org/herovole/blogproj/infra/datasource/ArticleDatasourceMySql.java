package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("articleDatasource")
public class ArticleDatasourceMySql implements ArticleDatasource {

    protected final AArticleRepository aArticleRepository;
    protected final AArticleHasTopicTagRepository aArticleHasTopicTagRepository;
    protected final AArticleHasCountryRepository aArticleHasCountryRepository;
    protected final AArticleHasEditorRepository aArticleHasEditorRepository;
    protected final ASourceCommentRepository aSourceCommentRepository;

    @Autowired
    public ArticleDatasourceMySql(AArticleRepository aArticleRepository,
                                  AArticleHasTopicTagRepository aArticleHasTopicTagRepository,
                                  AArticleHasCountryRepository aArticleHasCountryRepository,
                                  AArticleHasEditorRepository aArticleHasEditorRepository,
                                  ASourceCommentRepository aSourceCommentRepository) {
        this.aArticleRepository = aArticleRepository;
        this.aArticleHasTopicTagRepository = aArticleHasTopicTagRepository;
        this.aArticleHasCountryRepository = aArticleHasCountryRepository;
        this.aArticleHasEditorRepository = aArticleHasEditorRepository;
        this.aSourceCommentRepository = aSourceCommentRepository;
    }

    @Override
    public Article findById(IntegerId articleId) {
        AArticle jpaArticle = aArticleRepository.findById(articleId.longMemorySignature()).orElse(null);
        if (jpaArticle == null) return Article.empty();
        Article article = jpaArticle.toDomainObj();

        List<AArticleHasTopicTag> jpaTopicTags = aArticleHasTopicTagRepository.findByArticleId(articleId.longMemorySignature());
        IntegerIds topicTags = IntegerIds.of(jpaTopicTags.stream().map(AArticleHasTopicTag::toTopicTagId).toArray(IntegerId[]::new));

        List<AArticleHasCountry> jpaCountryCodes = aArticleHasCountryRepository.findByArticleId(articleId.longMemorySignature());
        CountryCodes coutryCodes = CountryCodes.of(jpaCountryCodes.stream().map(AArticleHasCountry::toIso2).toArray(CountryCode[]::new));

        List<AArticleHasEditor> jpaEditors = aArticleHasEditorRepository.findByArticleId(articleId.longMemorySignature());
        IntegerIds editors = IntegerIds.of(jpaEditors.stream().map(AArticleHasEditor::toEditorId).toArray(IntegerId[]::new));

        List<ASourceComment> jpaSourceComments = aSourceCommentRepository.findByArticleId(articleId.longMemorySignature());
        CommentUnits sourceComments = CommentUnits.of(jpaSourceComments.stream().map(ASourceComment::toDomainObj).toArray(CommentUnit[]::new));

        return article.append(topicTags, coutryCodes, editors, sourceComments);
    }

    @Override
    public IntegerIds searchByOptions(ArticleListSearchOption searchOption) {
        long[] ids = aArticleRepository.searchByOptions(
                searchOption.getKeywords().get(0).letterSignature(),
                searchOption.getKeywords().get(1).letterSignature(),
                searchOption.getKeywords().get(2).letterSignature(),
                searchOption.getDateRange().from().beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().to().shift(1).beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().from().toLocalDate(),
                searchOption.getDateRange().to().toLocalDate(),
                searchOption.getPagingRequest().getLimit(),
                searchOption.getPagingRequest().getOffset()
        );
        return IntegerIds.of(ids);
    }

    @Override
    public long countByOptions(ArticleListSearchOption searchOption) {
        return aArticleRepository.countByOptions(
                searchOption.getKeywords().get(0).letterSignature(),
                searchOption.getKeywords().get(1).letterSignature(),
                searchOption.getKeywords().get(2).letterSignature(),
                searchOption.getDateRange().from().beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().to().shift(1).beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().from().toLocalDate(),
                searchOption.getDateRange().to().toLocalDate()
        );
    }
}
