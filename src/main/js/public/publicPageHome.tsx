import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {TagService} from "../service/tags/tagService";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {ArticleListBody} from "../admin/fragment/articlelist/articleListBody";
import {HeadlinesMode} from "./fragment/articlelist/publicArticleHeadlines";

export const PublicPageHome: React.FC = () => {
    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [refresh, setRefresh] = React.useState(false);
    const reRender = () => {
        console.log("reload the page, flag:" + refresh);
        setRefresh(r => !r);
    }
    const load = async (): Promise<void> => {
        try {
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
        } catch (error) {
            console.error("error : ", error);
        }
    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, [refresh]);

    if (topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <PublicBasicLayout>
            <div>Loading...</div>
        </PublicBasicLayout>
    } else {
        return <PublicBasicLayout>
            <ArticleListBody
                mode={HeadlinesMode.LARGE}
                hasSearchMenu={false}
                directoryToIndividualPage={"/articles"}
                topicTagsOptions={topicTagsOptions}
                countryTagsOptions={countryTagsOptions}/>
            </PublicBasicLayout>
    }
};
