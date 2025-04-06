package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.article.RealArticle;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.AArticle;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasCountry;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasEditor;
import org.herovole.blogproj.infra.jpa.entity.AArticleHasTopicTag;
import org.herovole.blogproj.infra.jpa.entity.ASourceComment;
import org.herovole.blogproj.infra.jpa.entity.EmptyRecordException;
import org.herovole.blogproj.infra.jpa.entity.IncompatibleUpdateException;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasCountryRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasEditorRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleHasTopicTagRepository;
import org.herovole.blogproj.infra.jpa.repository.AArticleRepository;
import org.herovole.blogproj.infra.jpa.repository.ASourceCommentRepository;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("articleTransactionalDatasourceMySql")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class ArticleTransactionalDatasourceMySql extends ArticleDatasourceMySql implements ArticleTransactionalDatasource {

    private static final int ID_ORIGIN = 1;

    private final TransactionCache<Object> cacheInsert = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.insert(transaction);
        }
    };
    private final TransactionCache<Object> cacheUpdate = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.update(transaction);
        }
    };
    private final TransactionCache<String> cacheDelete = new TransactionCache<>() {
        @Override
        protected void doTransaction(String transaction, AppSession session) {
            session.executeSql(transaction);
        }
    };

    @Autowired
    protected ArticleTransactionalDatasourceMySql(
            AArticleRepository aArticleRepository,
            AArticleHasTopicTagRepository aArticleHasTopicTagRepository,
            AArticleHasCountryRepository aArticleHasCountryRepository,
            AArticleHasEditorRepository aArticleHasEditorRepository,
            ASourceCommentRepository aSourceCommentRepository,
            EUserCommentRepository eUserCommentRepository) {
        super(aArticleRepository, aArticleHasTopicTagRepository, aArticleHasCountryRepository, aArticleHasEditorRepository, aSourceCommentRepository, eUserCommentRepository);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions() +
                cacheUpdate.amountOfStackedTransactions() +
                cacheDelete.amountOfStackedTransactions();
    }

    @Override
    public void flush(AppSession session) {
        cacheInsert.flush(session);
        cacheUpdate.flush(session);
        cacheDelete.flush(session);
    }

    @Override
    public void insert(Article article) {
        if (article.isEmpty()) return;
        RealArticle article1 = (RealArticle) article;

        Long maxId = aArticleRepository.findMaxId();

        IntegerId articleId = IntegerId.valueOf(maxId == null ? ID_ORIGIN : maxId + 1);
        AArticle entity = AArticle.fromInsertDomainObj(articleId, article);
        AArticleHasCountry[] entitiesCountries = article1.getCountries().stream().map(e -> AArticleHasCountry.fromInsertDomainObj(articleId, e)).toArray(AArticleHasCountry[]::new);
        AArticleHasEditor[] entitiesEditors = article1.getEditors().stream().map(e -> AArticleHasEditor.fromInsertDomainObj(articleId, e)).toArray(AArticleHasEditor[]::new);
        AArticleHasTopicTag[] entitiesTopicTags = article1.getTopicTags().stream().map(e -> AArticleHasTopicTag.fromInsertDomainObj(articleId, e)).toArray(AArticleHasTopicTag[]::new);
        ASourceComment[] entitiesSourceComments = article1.getSourceComments().stream().map(e -> ASourceComment.fromInsertDomainObj(articleId, e)).toArray(ASourceComment[]::new);

        cacheInsert.add(entity);
        cacheInsert.add(entitiesCountries);
        cacheInsert.add(entitiesEditors);
        cacheInsert.add(entitiesTopicTags);
        cacheInsert.add(entitiesSourceComments);
    }

    @Override
    public void update(Article before, Article after) {
        if (after.isEmpty() || before.isEmpty()) throw new EmptyRecordException();
        RealArticle before1 = (RealArticle) before;
        RealArticle after1 = (RealArticle) after;

        if (!before1.getArticleId().equals(after1.getArticleId())) throw new IncompatibleUpdateException();
        IntegerId articleId = before1.getArticleId();

        CountryCodes countryCodesToInsert = before1.getCountries().lack(after1.getCountries());
        CountryCodes countryCodesToDelete = after1.getCountries().lack(before1.getCountries());

        IntegerIds editorsToInsert = before1.getEditors().unknownItemsOf(after1.getEditors());
        IntegerIds editorsToDelete = after1.getEditors().unknownItemsOf(before1.getEditors());

        IntegerIds topicTagsToInsert = before1.getTopicTags().unknownItemsOf(after1.getTopicTags());
        IntegerIds topicTagsToDelete = after1.getTopicTags().unknownItemsOf(before1.getTopicTags());

        CommentUnits sourceCommentsToInsert = before1.getSourceComments().unknownCommentsOf(after1.getSourceComments());
        CommentUnits sourceCommentsToDelete = after1.getSourceComments().unknownCommentsOf(before1.getSourceComments());
        CommentUnits sourceCommentsToUpdate = before1.getSourceComments().differentCommentsOf(after1.getSourceComments());

        AArticle entityToUpdate = AArticle.fromUpdateDomainObj(after);
        AArticleHasCountry[] entitiesCountriesToInsert = countryCodesToInsert.stream().map(e -> AArticleHasCountry.fromInsertDomainObj(articleId, e)).toArray(AArticleHasCountry[]::new);
        String[] sqlsCountriesToDelete = countryCodesToDelete.stream().map(e -> AArticleHasCountry.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        AArticleHasEditor[] entitiesEditorsToInsert = editorsToInsert.stream().map(e -> AArticleHasEditor.fromInsertDomainObj(articleId, e)).toArray(AArticleHasEditor[]::new);
        String[] sqlsEditorsToDelete = editorsToDelete.stream().map(e -> AArticleHasEditor.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        AArticleHasTopicTag[] entitiesTopicTagsToInsert = topicTagsToInsert.stream().map(e -> AArticleHasTopicTag.fromInsertDomainObj(articleId, e)).toArray(AArticleHasTopicTag[]::new);
        String[] sqlsTopicTagsToDelete = topicTagsToDelete.stream().map(e -> AArticleHasTopicTag.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        ASourceComment[] entitiesSourceCommentsToInsert = sourceCommentsToInsert.stream().map(e -> ASourceComment.fromInsertDomainObj(articleId, e)).toArray(ASourceComment[]::new);
        String[] sqlsSourceCommentsToDelete = sourceCommentsToDelete.stream().map(e -> ASourceComment.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        ASourceComment[] entitiesSourceCommentsToUpdate = sourceCommentsToUpdate.stream().map(e -> ASourceComment.fromUpdateDomainObj(articleId, before1.getSourceComments().findByInArticleCommentId(e.getCommentId()), e)).toArray(ASourceComment[]::new);

        cacheUpdate.add(entityToUpdate);
        cacheInsert.add(entitiesCountriesToInsert);
        cacheDelete.add(sqlsCountriesToDelete);
        cacheInsert.add(entitiesEditorsToInsert);
        cacheDelete.add(sqlsEditorsToDelete);
        cacheInsert.add(entitiesTopicTagsToInsert);
        cacheDelete.add(sqlsTopicTagsToDelete);
        cacheInsert.add(entitiesSourceCommentsToInsert);
        cacheDelete.add(sqlsSourceCommentsToDelete);
        cacheUpdate.add(entitiesSourceCommentsToUpdate);

    }
}
