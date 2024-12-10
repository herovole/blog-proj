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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ArticleTransactionalDatasourceMySql extends ArticleDatasourceMySql implements ArticleTransactionalDatasource {

    private static final TransactionCache<Object> cacheInsert = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.insert(transaction);
        }
    };
    private static final TransactionCache<Object> cacheUpdate = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.update(transaction);
        }
    };
    private static final TransactionCache<String> cacheDelete = new TransactionCache<>() {
        @Override
        protected void doTransaction(String transaction, AppSession session) {
            session.executeSql(transaction);
        }
    };
    private final AtomicLong maxArticleId;

    @Autowired
    protected ArticleTransactionalDatasourceMySql(AArticleRepository aArticleRepository, AArticleHasTopicTagRepository aArticleHasTopicTagRepository, AArticleHasCountryRepository aArticleHasCountryRepository, AArticleHasEditorRepository aArticleHasEditorRepository, ASourceCommentRepository aSourceCommentRepository) {
        super(aArticleRepository, aArticleHasTopicTagRepository, aArticleHasCountryRepository, aArticleHasEditorRepository, aSourceCommentRepository);
        Long maxId = aArticleRepository.findMaxId();
        this.maxArticleId = maxId == null ? new AtomicLong(-1) : new AtomicLong(maxId);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions() +
                cacheInsert.amountOfStackedTransactions() +
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

        IntegerId articleId = IntegerId.valueOf(maxArticleId.incrementAndGet());
        AArticle entity = AArticle.fromInsertDomainObj(articleId, article);
        AArticleHasCountry[] entitiesCountries = article1.getCountries().stream().map(e -> AArticleHasCountry.fromInsertDomainObj(articleId, e)).toArray(AArticleHasCountry[]::new);
        AArticleHasEditor[] entitiesEditors = article1.getEditors().stream().map(e -> AArticleHasEditor.fromInsertDomainObj(articleId, e)).toArray(AArticleHasEditor[]::new);
        AArticleHasTopicTag[] entitiesTopicTags = article1.getTopicTags().stream().map(e -> AArticleHasTopicTag.fromInsertDomainObj(articleId, e)).toArray(AArticleHasTopicTag[]::new);
        ASourceComment[] entitiesSourceComments = article1.getOriginalComments().stream().map(e -> ASourceComment.fromInsertDomainObj(articleId, e)).toArray(ASourceComment[]::new);

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

        IntegerIds editorsToInsert = before1.getEditors().lack(after1.getEditors());
        IntegerIds editorsToDelete = after1.getEditors().lack(before1.getEditors());

        IntegerIds topicTagsToInsert = before1.getTopicTags().lack(after1.getTopicTags());
        IntegerIds topicTagsToDelete = after1.getTopicTags().lack(before1.getTopicTags());

        CommentUnits sourceCommentsToInsert = before1.getOriginalComments().lacksInId(after1.getOriginalComments());
        CommentUnits sourceCommentsToDelete = after1.getOriginalComments().lacksInId(before1.getOriginalComments());
        CommentUnits sourceCommentsToUpdate = before1.getOriginalComments().differ(after1.getOriginalComments());

        AArticle entityToUpdate = AArticle.fromUpdateDomainObj(after);
        AArticleHasCountry[] entitiesCountriesToInsert = countryCodesToInsert.stream().map(e -> AArticleHasCountry.fromInsertDomainObj(articleId, e)).toArray(AArticleHasCountry[]::new);
        String[] sqlsCountriesToDelete = countryCodesToDelete.stream().map(e -> AArticleHasCountry.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        AArticleHasEditor[] entitiesEditorsToInsert = editorsToInsert.stream().map(e -> AArticleHasEditor.fromInsertDomainObj(articleId, e)).toArray(AArticleHasEditor[]::new);
        String[] sqlsEditorsToDelete = editorsToDelete.stream().map(e -> AArticleHasEditor.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        AArticleHasTopicTag[] entitiesTopicTagsToInsert = topicTagsToInsert.stream().map(e -> AArticleHasTopicTag.fromInsertDomainObj(articleId, e)).toArray(AArticleHasTopicTag[]::new);
        String[] sqlsTopicTagsToDelete = topicTagsToDelete.stream().map(e -> AArticleHasTopicTag.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        ASourceComment[] entitiesSourceCommentsToInsert = sourceCommentsToInsert.stream().map(e -> ASourceComment.fromInsertDomainObj(articleId, e)).toArray(ASourceComment[]::new);
        String[] sqlsSourceCommentsToDelete = sourceCommentsToDelete.stream().map(e -> ASourceComment.fromDeleteDomainObj(articleId, e)).toArray(String[]::new);
        ASourceComment[] entitiesSourceCommentsToUpdate = sourceCommentsToUpdate.stream().map(e -> ASourceComment.fromUpdateDomainObj(articleId, e)).toArray(ASourceComment[]::new);

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
