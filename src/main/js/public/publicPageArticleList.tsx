import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleListBody} from "../admin/fragment/articlelist/articleListBody";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {TagService} from "../service/tags/tagService";

export const PublicPageArticleList = () => {

    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState(TagUnits.empty());


    const load = async (): Promise<void> => {

        const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
        setTopicTagsOptions(topicOutput.getTagUnits());

        const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
        setCountryTagsOptions(countriesOutput.getTagUnits());

        if (!topicOutput.isSuccessful() || !countriesOutput.isSuccessful()) {
            console.error("Tags failed to load.");
        }

    };
    useEffect(() => {
        load().then();
    }, []);

    if (topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <PublicBasicLayout>
                <ArticleListBody
                    directoryToIndividualPage={"/articles"}
                    hasSearchMenu={true}
                    topicTagsOptions={topicTagsOptions}
                    countryTagsOptions={countryTagsOptions}/>
            </PublicBasicLayout>
        );
    }
};
