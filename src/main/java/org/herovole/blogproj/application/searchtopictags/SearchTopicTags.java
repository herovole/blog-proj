package org.herovole.blogproj.application.searchtopictags;

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

    @Autowired
    public SearchTopicTags(@Qualifier("topicTagDatasource") TopicTagDatasource topicTagDatasource) {
        this.topicTagDatasource = topicTagDatasource;
    }

    public SearchTopicTagsOutput process(SearchTopicTagsInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        PagingRequest option = input.getPagingRequest();
        long total = topicTagDatasource.countAll();
        TagUnits tagUnits = topicTagDatasource.search(option);
        logger.info("job successful.");

        return SearchTopicTagsOutput.builder()
                .tagUnits(tagUnits)
                .total(total)
                .build();
    }
}
