package org.herovole.blogproj.application.tag.searchtopictags;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.topic.TagUnits;
import org.herovole.blogproj.domain.tag.topic.TopicTagDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchTopicTags {

    private static final Logger logger = LoggerFactory.getLogger(SearchTopicTags.class.getSimpleName());

    private final TopicTagDatasource topicTagDatasource;
    private final GenericPresenter<SearchTopicTagsOutput> presenter;

    @Autowired
    public SearchTopicTags(@Qualifier("topicTagDatasource") TopicTagDatasource topicTagDatasource, GenericPresenter<SearchTopicTagsOutput> presenter) {
        this.topicTagDatasource = topicTagDatasource;
        this.presenter = presenter;
    }

    public void process(SearchTopicTagsInput input) {
        logger.info("interpreted post : {}", input);
        PagingRequest option = input.getPagingRequest();
        long total = topicTagDatasource.countAll();
        TagUnits tagUnits = topicTagDatasource.search(input.isDetailed(), option);
        logger.info("job successful.");

        SearchTopicTagsOutput output = SearchTopicTagsOutput.builder()
                .tagUnits(tagUnits)
                .total(total)
                .build();
        this.presenter.setContent(output);
    }
}
