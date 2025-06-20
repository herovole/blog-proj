import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {DateSelectingForm} from './fragment/atomic/dateSelectingForm';
import {TagSelectingForm} from './fragment/atomic/tagselectingform/tagSelectingForm';
import {ImageSelectingModal} from './fragment/image/imageSelectingModal';
import {TagButton} from "./fragment/atomic/tagselectingform/tagButton";
import {ArticleSummaryList} from "../domain/articlelist/articleSummaryList";
import {PublicArticleHeadlines} from "../public/fragment/articlelist/publicArticleHeadlines";
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {SearchArticlesOutput} from "../service/articles/searchArticlesOutput";
import {RootElementId} from "../domain/elementId/rootElementId";

console.log("sandbox.js");

export const Sandbox: React.FC = () => {
    const [topicTagsOptions, setTopicTagsOptions] = useState(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = useState(TagUnits.empty());
    const [articles, setArticles] = useState(ArticleSummaryList.empty());

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {

                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {"topicTags.page": 1, "topicTags.itemsPerPage": 10000, "topicTags.isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const topicUnitList: TagUnits = TagUnits.fromHash(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const countryUnitList: TagUnits = TagUnits.fromHash(countryResponse.data);
                setCountryTagsOptions(countryUnitList);

                const articleSummariesResponse = await axios.get("/api/v1/articles", {
                    params: {"articleList.itemsPerPage": 10000, "articleList.page": 1,},
                    headers: {Accept: "application/json"},
                });
                const searchArticlesOutput: SearchArticlesOutput = SearchArticlesOutput.fromHash(articleSummariesResponse.data);
                setArticles(searchArticlesOutput.articleSummaries);

            } catch (error) {
                console.error("error : tagSelectingForm", error);
            }
        };
        fetchTagsOptions();
    }, []);

    const selectedTags = ["af"];

    return <div>
        <div>
            test1: imageSelectingModal
            <ImageSelectingModal
                postKey={RootElementId.valueOf("test")}
            />
        </div>
        <div>
            test2: Headlines
            <PublicArticleHeadlines
                articles={articles}
                directoryToIndividualPage={""}
                topicTagList={topicTagsOptions}
                countryTagList={countryTagsOptions}
            />
        </div>
        <div>
            test3:
            <DateSelectingForm
                postKey={RootElementId.valueOf("dsf")}
            />
        </div>
        <div>
            test4:
            <TagSelectingForm
                postKey={RootElementId.valueOf("tsf")}
                candidates={countryTagsOptions || TagUnits.empty()}
                children={selectedTags}
            />
        </div>
        <div>
            test5:
            <TagButton
                unit={topicTagsOptions.getTagUnit("1")}
                searchBaseUrl={"ccc"}
                searchKey={"aaa"}
            />

        </div>

    </div>

}