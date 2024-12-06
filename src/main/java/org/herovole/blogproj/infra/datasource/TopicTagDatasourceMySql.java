package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.tag.topic.TagUnits;
import org.herovole.blogproj.domain.tag.topic.TopicTagDatasource;
import org.herovole.blogproj.infra.jpa.entity.ATopicTag;
import org.herovole.blogproj.infra.jpa.repository.ATopicTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("topicTagDatasource")
public class TopicTagDatasourceMySql implements TopicTagDatasource {

    private final ATopicTagRepository aTopicTagRepository;

    @Autowired
    public TopicTagDatasourceMySql(ATopicTagRepository aTopicTagRepository) {
        this.aTopicTagRepository = aTopicTagRepository;
    }


    @Override
    public TagUnit findById(IntegerId tagId) {
        if (tagId.isEmpty()) return TagUnit.empty();
        ATopicTag aTopicTag = aTopicTagRepository.findByTopicTagId(tagId.longMemorySignature());
        if (aTopicTag == null) return TagUnit.empty();
        return aTopicTag.toDomainObj();
    }

    @Override
    public TagUnits search(boolean isDetailed, PagingRequest pagingRequest) {
        if (isDetailed) {
            List<ATopicTag.ATopicTagWithStat> topicTagList = aTopicTagRepository.searchByOptionsWithStat(pagingRequest.getLimit(), pagingRequest.getOffset());
            return TagUnits.of(topicTagList.stream().map(ATopicTag.ATopicTagWithStat::toDomainObj).toArray(TagUnit[]::new));
        } else {
            List<ATopicTag> topicTagList = aTopicTagRepository.searchByOptions(pagingRequest.getLimit(), pagingRequest.getOffset());
            return TagUnits.of(topicTagList.stream().map(ATopicTag::toDomainObj).toArray(TagUnit[]::new));
        }
    }

    @Override
    public long countAll() {
        return aTopicTagRepository.countAll();
    }
}
