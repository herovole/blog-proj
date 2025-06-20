package org.herovole.blogproj.application.tag.edittopictags;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.tag.topic.TagUnits;
import org.herovole.blogproj.domain.tag.topic.TopicTagDatasource;
import org.herovole.blogproj.domain.tag.topic.TopicTagTransactionalDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EditTopicTags {

    private static final Logger logger = LoggerFactory.getLogger(EditTopicTags.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final TopicTagDatasource topicTagDatasource;
    private final TopicTagTransactionalDatasource topicTagTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public EditTopicTags(AppSessionFactory sessionFactory,
                         @Qualifier("topicTagDatasource") TopicTagDatasource topicTagDatasource,
                         TopicTagTransactionalDatasource topicTagTransactionalDatasource, GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.topicTagDatasource = topicTagDatasource;
        this.topicTagTransactionalDatasource = topicTagTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(EditTopicTagsInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        TagUnits topicTags = input.getTagUnits();
        for (TagUnit topicTag : topicTags) {
            if (topicTag.isEmpty()) continue;
            TagUnit before = topicTagDatasource.findById(topicTag.getId());
            if (before.hasSameContentWith(topicTag)) continue;
            if (before.isEmpty()) {
                logger.info("insert topic tag : {}", topicTag);
                topicTagTransactionalDatasource.insert(topicTag);
            } else {
                logger.info("update topic tag : {} -> {}", before, topicTag);
                topicTagTransactionalDatasource.update(before, topicTag);
            }
            logger.info("total transaction number : {}", topicTagTransactionalDatasource.amountOfCachedTransactions());
        }

        try (AppSession session = sessionFactory.createSession()) {
            topicTagTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");

    }
}
