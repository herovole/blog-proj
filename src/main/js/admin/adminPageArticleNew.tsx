import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {RootElementId} from "../domain/elementId/rootElementId";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {TagService} from "../service/tags/tagService";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticleNew: React.FC = () => {
        const tagService: TagService = new TagService();
        const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
        const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

        const load = async (): Promise<void> => {

            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
            if (topicOutput.isSuccessful()) {
                setTopicTagsOptions(topicOutput.getTagUnits());
            } else {
                console.error("failed to fetch topic tags");
                return;
            }

            const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
            if (countriesOutput.isSuccessful()) {
                setCountryTagsOptions(countriesOutput.getTagUnits());
            } else {
                console.error("failed to fetch country tags");
            }
        };
        useEffect(() => {
            load().then();
        }, []);

        return (
            <AdminBasicLayout>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                    topicTagsOptions={topicTagsOptions}
                    countryTagsOptions={countryTagsOptions}
                />
            </AdminBasicLayout>
        );
    }
;
