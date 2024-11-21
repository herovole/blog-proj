package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.ArticleEditingPage;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.article.RealArticleEditingPage;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.CountryCodes;
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

import java.util.concurrent.atomic.AtomicLong;

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
    private final AtomicLong maxArticleId;

    protected ArticleTransactionalDatasourceMySql(AArticleRepository aArticleRepository, AArticleHasTopicTagRepository aArticleHasTopicTagRepository, AArticleHasCountryRepository aArticleHasCountryRepository, AArticleHasEditorRepository aArticleHasEditorRepository, ASourceCommentRepository aSourceCommentRepository) {
        super(aArticleRepository, aArticleHasTopicTagRepository, aArticleHasCountryRepository, aArticleHasEditorRepository, aSourceCommentRepository);
        Long maxId = aArticleRepository.findMaxId();
        this.maxArticleId = maxId == null ? new AtomicLong(-1) : new AtomicLong(maxId);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions();
    }

    @Override
    public void flush(AppSession session) {
        cacheInsert.flush(session);
        cacheUpdate.flush(session);
    }

    @Override
    public void insert(ArticleEditingPage article) {
        if (article.isEmpty()) return;
        RealArticleEditingPage article1 = (RealArticleEditingPage) article;

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
    public void update(ArticleEditingPage before, ArticleEditingPage after) {
        if (after.isEmpty() || before.isEmpty()) throw new EmptyRecordException();
        RealArticleEditingPage before1 = (RealArticleEditingPage) before;
        RealArticleEditingPage after1 = (RealArticleEditingPage) after;

        if (!before1.getArticleId().equals(after1.getArticleId())) throw new IncompatibleUpdateException();
        IntegerId articleId = before1.getArticleId();

        CountryCodes countryCodesToAdd = before1.getCountries().lack(after1.getCountries());
        CountryCodes countryCodesToDelete = after1.getCountries().lack(before1.getCountries());

        IntegerIds editorsToAdd = before1.getEditors().lack(after1.getEditors());
        IntegerIds editorsToDelete = after1.getEditors().lack(before1.getEditors());

        IntegerIds topicTagsToAdd = before1.getTopicTags().lack(after1.getTopicTags());
        IntegerIds topicTagsToDelete = after1.getTopicTags().lack(before1.getTopicTags());

        CommentUnits sourceCommentsToAdd = before1.getOriginalComments().lacksInId(after1.getOriginalComments());
        CommentUnits sourceCommentsToDelete = after1.getOriginalComments().lacksInId(before1.getOriginalComments());
        CommentUnits sourceCommentsToUpdate = before1.getOriginalComments().differ(after1.getOriginalComments());

        AArticle entityToUpdate = AArticle.fromUpdateDomainObj(before);
        AArticleHasCountry[] entitiesCountriesToInsert = countryCodesToAdd.stream().map(e -> AArticleHasCountry.fromInsertDomainObj(articleId, e)).toArray(AArticleHasCountry[]::new);
        AArticleHasEditor[] entitiesEditorsToInsert = editorsToAdd.stream().map(e -> AArticleHasEditor.fromInsertDomainObj(articleId, e)).toArray(AArticleHasEditor[]::new);
        AArticleHasTopicTag[] entitiesTopicTagsToInsert = topicTagsToAdd.stream().map(e -> AArticleHasTopicTag.fromInsertDomainObj(articleId, e)).toArray(AArticleHasTopicTag[]::new);
        ASourceComment[] entitiesSourceCommentsToInsert = sourceCommentsToAdd.stream().map(e -> ASourceComment.fromInsertDomainObj(articleId, e)).toArray(ASourceComment[]::new);
        ASourceComment[] entitiesSourceCommentsToUpdate = sourceCommentsToUpdate.stream().map(e -> ASourceComment.fromUpdateDomainObj(articleId, e)).toArray(ASourceComment[]::new);

        cacheUpdate.add(entityToUpdate);
        cacheInsert.add(entitiesCountriesToInsert);
        cacheInsert.add(entitiesEditorsToInsert);
        cacheInsert.add(entitiesTopicTagsToInsert);

    }
}
