package org.herovole.blogproj.application.edittopictags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.tag.topic.TagUnits;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EditTopicTagsInput {

    private static final String API_KEY_TOPIC_TAGS = "topicTags";

    public static EditTopicTagsInput fromFormContent(FormContent formContent) {
        FormContent children = formContent.getChildren(API_KEY_TOPIC_TAGS);
        return new EditTopicTagsInput(TagUnits.fromFormContent(children));
    }

    private final TagUnits tagUnits;
}
