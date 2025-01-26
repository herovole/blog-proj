import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {ArticleListBody} from "./fragment/articlelist/articleListBody";
import {TagService} from "../service/tags/tagService";
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";

export const AdminPageArticleList: React.FC = () => {

    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState(TagUnits.empty());


    const load = async (): Promise<void> => {

        const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
        if (topicOutput.isSuccessful()) {
            setTopicTagsOptions(topicOutput.getTagUnits());
        } else {
            console.error(topicOutput.getMessage("topic tags retrieval"));
        }

        const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
        if (countriesOutput.isSuccessful()) {
            setCountryTagsOptions(countriesOutput.getTagUnits());
        } else {
            console.error(countriesOutput.getMessage("country tags retrieval"));
        }

    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, []);

    if (topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <AdminBasicLayout>
                <ArticleListBody
                    hasSearchMenu={true}
                    directoryToIndividualPage={"/admin/articles"}
                    topicTagsOptions={topicTagsOptions}
                    countryTagsOptions={countryTagsOptions}
                />
            </AdminBasicLayout>
        );
    }
};
